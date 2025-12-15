### TO:DO API

0. Install [Leiningen](https://leiningen.org/#install)
1. To run:  
   1.1. run `clj`  
   1.2. run `(require 'main)`  
   1.3. run `(main/start-dev)`  
2. To restart:  
   2.1. run `(require :reload 'main)`  
   2.2. run `(main/restart)`  
3. To create a list:  
   3.1. run `(dissoc (main/test-request :post "/todo") :headers)`  
4. To view the database:  
   4.1 run `@main/database`  

## First exercise: Create a default name  "Nubank"
1. Failed: {:status 201, :body "{:name nil, :items {}}"}  
2. Fix it: {:status 201, :body "{:name "Nubank", :items {}}"}  
* Tip: There are two interceptors "db-interceptor" and "list-create". But the db-interceptor is mocking a real db, everything is already done!  

## Second exercise: Parse the name to Camel case using a new interceptor
0. `(dissoc (main/test-request :post "/todo?name=Nubank_first") :headers)`  
1. Failed: {:status 201, :body "{:name  "Nubank_first", :items {}}"}  
2. Fix it: {:status 201, :body "{:name "nubankFirst"}"}  
  
## Third exercise: Parse the name to Camel case using a new interceptor and the camel-snake-kebab library
0. `(dissoc (main/test-request :post "/todo?name=Nubank_first_todo") :headers)`  
1. Failed: {:status 201, :body "{:name  "Nubank_first_todo", :items {}}"}  
2. Fix it: {:status 201, :body "{:name "nubankFirstTodo"}"}  
* Tip: [doc](https://clj-commons.org/camel-snake-kebab/)  

## Fourth exercise: Parse the item name to Camel case using a new interceptor
0. `(dissoc (main/test-request :post "/todo/l15550?name=first_item") :headers)`  
1. Failed: {:status 200, :body "{:name \"first_item\", :done? false}"}  
2. Fix it: {:status 200, :body "{:name \"firstItem\", :done? false}"}  
