# Dascalu Stefan-Nicolae 321CA 2023-2024
# used skel from ocw

## Implementation
### Wrapped
I implemented the command using the **Strategy** pattern. Each class (artist, host, user) instantiates its own wrapped strategy
and exposes a high-level wrapped method that calls the respective user's strategy. The three subclasses of
statistics encapsulate the statistical data; to record the number of listens, I used an **Observer** pattern
to notify the Player and User of song changes.

### Monetization
I used the **Strategy** pattern; there are two types of strategies, free and premium, and I switch between them when
the user upgrades/demotes.

### Notifications
I implemented the notification system with an **Observer** system. ContentCreator contains a list of subscribers and
a method through which a user can subscribe to the respective creator, and Artists and Hosts notify subscribers
at the specified times in the requirements. Users also contain a notification list (a class that contains the description
and name of the notification).

### Recommendations
For the implementation of recommendations, I used the **Command** pattern. Each type of recommendation
represents a command (subclass) that receives an instance of the User it processes
in the constructor. To simplify the creation of instances, I used a static **Factory** pattern.

### Page Navigation
I implemented page navigation with 2 stacks, one for forward pages and the other for back pages.
I delegated all methods and data related to pages to a separate class (PageNavigator), and the user
contains an instance of it and exposes high-level methods to access/display pages further.