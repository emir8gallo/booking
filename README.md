# booking system

A booking is when a guest selects a start and end date and submits a reservation on a property.

A block IS when the propertv owner or manager selects a range or days during which no guest can make
a booking e.g. the owner wants to use the property for themselves, or the property manager needs to schedule the repainting of a few rooms).

Provided following functionalities:
- Creating, deleting and updating a booking
- Creating, deleting and updating a block

Bookings can be updated (dates, guest data), canceled and rebooked. 
Blocks can be created, updated and deleted. 
Blocks can overlap with each other. Provide logic to prevent bookings overlapping (in terms of dates and property) with other bookings or blocks.
Database

Java 17
H2 in memory DB used.

Potential improvements:
- Adding authentication and authorization
- Improve error handling (generic error handler)
- Add logging
- Specific case when property is booked by guest and owner blocks those specific dates is not covered (not mentioned in task description)
  - potential solution in this case -> cancel the booking and add block, potentialy inform user (if there was an email or something similar)


Additional resource:
- Initial Flyway Migration which inserts 2 properties so the app is usable
- Postman collection with basic APIs (create, update, delete both bookings and blocks)
