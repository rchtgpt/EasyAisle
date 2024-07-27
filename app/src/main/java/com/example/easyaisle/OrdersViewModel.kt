import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.easyaisle.Order
import com.google.firebase.database.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
class OrdersViewModel : ViewModel() {
    private val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    private val esriFreshRef: DatabaseReference = database.getReference("EsriFresh")
    private val costcoRef: DatabaseReference = database.getReference("Costco")
    private val traderJoesRef: DatabaseReference = database.getReference("Trader Joes")

    private val _costcoListOfOrders = MutableStateFlow<List<Order>>(emptyList())
    val costcoListOfOrders: StateFlow<List<Order>> = _costcoListOfOrders

    private val _esriFreshListOfOrders = MutableStateFlow<List<Order>>(emptyList())
    val esriFreshListOfOrders: StateFlow<List<Order>> = _esriFreshListOfOrders

    private val _traderJoesListOfOrders = MutableStateFlow<List<Order>>(emptyList())
    val traderJoesListOfOrders: StateFlow<List<Order>> = _traderJoesListOfOrders

    init {
        fetchOrders()
    }

    private fun fetchOrders() {
        fetchEsriFreshOrders()
        fetchCostcoOrders()
        fetchTraderJoesOrders()
    }

    private fun fetchEsriFreshOrders() {
        esriFreshRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                viewModelScope.launch {
                    val gson = Gson()
                    val jsonString = gson.toJson(snapshot.value)

                    // Define the type for the list of Order
                    val ordersListType = object : TypeToken<List<Order>>() {}.type

                    // Convert the JSON string to a list of Order
                    val orders: List<Order>? = gson.fromJson(jsonString, ordersListType)

                    // Update the state flow with the new list of orders
                    if (orders != null) {
                        orders.forEach { it.setIconResId() } // Set the iconResId for each order
                        _esriFreshListOfOrders.value = orders
                    }

                    // Print the list to verify
                    Log.d("Firebase Data", "EsriFresh Orders: ${_esriFreshListOfOrders.value}")
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("Firebase Data", "loadPost:onCancelled", error.toException())
            }
        })
    }

    private fun fetchCostcoOrders() {
        costcoRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                viewModelScope.launch {
                    val gson = Gson()
                    val jsonString = gson.toJson(snapshot.value)

                    // Define the type for the list of Order
                    val ordersListType = object : TypeToken<List<Order>>() {}.type

                    // Convert the JSON string to a list of Order
                    val orders: List<Order>? = gson.fromJson(jsonString, ordersListType)

                    // Update the state flow with the new list of orders
                    if (orders != null) {
                        orders.forEach { it.setIconResId() } // Set the iconResId for each order
                        _costcoListOfOrders.value = orders
                    }

                    // Print the list to verify
                    Log.d("Firebase Data", "Costco Orders: ${_costcoListOfOrders.value}")
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("Firebase Data", "loadPost:onCancelled", error.toException())
            }
        })
    }

    private fun fetchTraderJoesOrders() {
        traderJoesRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                viewModelScope.launch {
                    val gson = Gson()
                    val jsonString = gson.toJson(snapshot.value)

                    // Define the type for the list of Order
                    val ordersListType = object : TypeToken<List<Order>>() {}.type

                    // Convert the JSON string to a list of Order
                    val orders: List<Order>? = gson.fromJson(jsonString, ordersListType)

                    // Update the state flow with the new list of orders
                    if (orders != null) {
                        orders.forEach { it.setIconResId() } // Set the iconResId for each order
                        _traderJoesListOfOrders.value = orders
                    }

                    // Print the list to verify
                    Log.d("Firebase Data", "Trader Joes Orders: ${_traderJoesListOfOrders.value}")
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("Firebase Data", "loadPost:onCancelled", error.toException())
            }
        })
    }
}

