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
