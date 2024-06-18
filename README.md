# Lab3SQLite_CSI460_MM
 
This is a rudimentary version of an application to demonstrate the use of a SQLite database in Android. The application allows you to create a vehicle listing as if you were going to sell it. It also allows for reading, updating, and deleting car listings in the database.


Starting the application brings up a simple page where we can enter information to create a vehicle listing. As we can see, there is three buttons, one for adding a car image, one for submitting the form, and one to see the full car list.
![mainPage](https://github.com/MMahar5/Lab3SQLite_CSI460_MM/assets/97249776/ebc6e3e8-5507-4055-ac12-1421a72c4094)

To add photos from the emulator like you would from your phone, I had to add images to the path "storage/emulated/0/(create folder here)" within the device explorer. Then when clicking "Add Image", I am shown this page where I can select my photo.
![selectphoto](https://github.com/MMahar5/Lab3SQLite_CSI460_MM/assets/97249776/02289a06-19cc-4ff2-b5db-53485d260628)

This is what it looks like after adding the rest of the car listing information 
![main](https://github.com/MMahar5/Lab3SQLite_CSI460_MM/assets/97249776/57e626c1-b669-41c6-b9d3-b1b21f85af17)

There are validation checks for the text fields to make sure the information is correct and not missing. If not, then it will show a toast message at the bottom, depending on the issue. The only field that is not required is the image, in which case, it will use the default placeholder.
![missingFields](https://github.com/MMahar5/Lab3SQLite_CSI460_MM/assets/97249776/c0b0af52-6382-4c78-9af2-f541508625ba)

After clicking the "Submit Ad" button to save the information and then "See All Cars", we can see the full list of car listings as well as the truck that we just created. 

![addedToAll-2](https://github.com/MMahar5/Lab3SQLite_CSI460_MM/assets/97249776/4bd4bd7e-a49b-41c6-9f8a-3a8d7afdf926)

We can click on any car listing in order to make edits. As you can see, I just clicked on the same one that I just made, which takes us to a page that is similar but with the title of "Edit Listing" and and we have a couple different buttons that say "Update" and "Delete". I've already entered some new information, I lowered the price from $62,350 to $61,500 and changed the image to a slightly different one.
![withedit-3 2](https://github.com/MMahar5/Lab3SQLite_CSI460_MM/assets/97249776/66204e1c-a9fa-405c-b93d-c764dfa84166)

After clicking update, we can see that the changes made (price and image) are reflected in our list.
![updated-4](https://github.com/MMahar5/Lab3SQLite_CSI460_MM/assets/97249776/59e0d0a6-b3b5-4821-9344-93ecb1862aa8)

If I want to go back to edit and delete the vehicle, I just have to click "Delete" and it will show an Alert Dialog to confirm. After clicking yes, we can see that the truck has been removed from our list.

![delete2](https://github.com/MMahar5/Lab3SQLite_CSI460_MM/assets/97249776/6192a125-76ec-460e-800a-28de9ba83b17)  ![allcarsAfterDelete](https://github.com/MMahar5/Lab3SQLite_CSI460_MM/assets/97249776/fc9fd98f-1b27-4648-b75b-89119aebaaaa)

By using DB Browser, I can view our SQLite database to show different tables, the fields, data points, data types, and so on. This is a fairly simple database, so we just have one table called "myCars". As you can see, the truck I created is not here because I deleted it, but the rest of the cars (only 3) are all shown
![db](https://github.com/MMahar5/Lab3SQLite_CSI460_MM/assets/97249776/3069d807-0b71-4bea-af4b-3667274af0c8)

This shows the project structure. The CarListing is our object, the SeeAllCars is the page showing the list of cars, the UpdateCar is our edit page, our RclAdapter to help display each car (car_listing_item.xml) in a recycler view, and a DbHandler to help manager our SQLite database.
![projectFiles](https://github.com/MMahar5/Lab3SQLite_CSI460_MM/assets/97249776/359592ca-f85e-43f3-8d8d-11c544dc7e05)
