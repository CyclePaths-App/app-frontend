package com.cyclepaths.www

/**
 * This class is just for testing out the CO2 calculation.
 */
class CO2TestData {
    // commonMain
    object DummyData {
        val dummyUser = User(
            id = 1,
            username = "BikeBro",
            first_name = "Fredrick",
            last_name = "Pizza",
            email = "BikeBro@gmail.com",
            password = "PizzaLife"
        )

        val dummyTrips = listOf(
            Trip(
                id = 1,
                userId = dummyUser.id,
                distance = 10,
                tripType = TripType.bike
            ),
            Trip(
                id = 3,
                userId = dummyUser.id,
                distance = 2,
                tripType = TripType.walk
            ),
            Trip(
                id = 4,
                userId = dummyUser.id,
                distance = 15,
                tripType = TripType.bike
            )
        )
    }
}