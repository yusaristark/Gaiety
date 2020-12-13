package Model.ClientData.Orders.Event

data class Event (
    val id: Int,
    val name: String,
    val city: String,
    val location: Location,
    val starts_at: String,
    val ends_at: String
)