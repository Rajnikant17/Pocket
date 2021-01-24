# Pocket

Library used :

1 Retrofit
2 Navigation component
3 Hilt Dependency injection
4 Data binding
5 LiveData
6 ViewBinding

Brief work flow :

There are two modules in this project , "App" and "ApiServices" . "App" is the default module and a separate module is being created for Network based classes.
Navigation component is being used to achieve single activity app . There are three Fragments in the App ,
First is Home Page fragment, where posts api is being called and after that response is grouped based on the user id and then displayed using recyclerview.
On clicking any row in recyclerview , user_id will be passed to the second fragment and then over there user details api will be called and then shown in the
recyclerview. And if due to any reason api is not successfull due to some reason such as network fail or any other reason then a new "Error Fragment" will be
opened where  a retry button is there on clicking the retry button again api will get called.


Implemented the Hilt Dependency inject for injecting the Objects at various places wherever it is required.
Used MVVM architecture.