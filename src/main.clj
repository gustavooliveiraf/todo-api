(ns main
    (:require [io.pedestal.http :as http]
     [io.pedestal.http.route :as route]
     [io.pedestal.test :as test]
     [camel-snake-kebab.core :as csk]))

(defn response [status body & {:as headers}]
      {:status status :body body :headers headers})

(def ok (partial response 200))
(def created (partial response 201))
(def accepted (partial response 202))

;;;
;;; "Database" functions
;;;
(defonce database (atom {}))

(defn find-list-by-id [dbval db-id]
      (get dbval db-id))

(defn find-list-item-by-ids [dbval list-id item-id]
      (get-in dbval [list-id :items item-id] nil))

(defn list-item-add
      [dbval list-id item-id new-item]
      (if (contains? dbval list-id)
        (assoc-in dbval [list-id :items item-id] new-item)
        dbval))

(def db-interceptor
  {:name :database-interceptor
   :enter
   (fn [context]
       (update context :request assoc :database @database))
   :leave
   (fn [context]
       (if-let [[op & args] (:tx-data context)]
               (do
                 (apply swap! database op args)
                 (assoc-in context [:request :database] @database))
               context))})

;;;
;;; Domain functions
;;;
(defn make-list [nm]
      {:name nm
       :items {}})

(defn make-list-item [nm]
      {:name nm
       :done? false})

;;;
;;; API Interceptors
;;;
(def error-interceptor
  {:name :error-interceptor
   :error
   (fn [context exception]
       context)})

(def list-create
  {:name :list-create
   :enter
   (fn [context]
       (let [parse-name (.toString (get-in context [:request :query-params :name]))
             divide-by-zero (/ 10 0)]
            context))})

(def routes
  (route/expand-routes
   #{["/todo" :post [error-interceptor list-create]]}))

(def service-map
  {::http/routes routes
   ::http/type :jetty
   ::http/port 8890})

(defn start []
      (http/start (http/create-server service-map)))

;; For interactive development
(defonce server (atom nil))

(defn start-dev []
      (reset! server
              (http/start (http/create-server
                           (assoc service-map
                                  ::http/join? false)))))

(defn stop-dev []
      (http/stop @server))

(defn restart []
      (stop-dev)
      (start-dev))

(defn test-request [verb url]
      (io.pedestal.test/response-for (::http/service-fn @server) verb url))
