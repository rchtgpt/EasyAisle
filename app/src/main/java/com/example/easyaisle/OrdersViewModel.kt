import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.easyaisle.Order
import com.google.firebase.database.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
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

                    val ordersListType = object : TypeToken<List<Order>>() {}.type
                    val orders: List<Order>? = gson.fromJson(jsonString, ordersListType)

                    if (orders != null) {
                        orders.forEach { it.setIconResId() }
                        _esriFreshListOfOrders.value = orders
                    }

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

                    val ordersListType = object : TypeToken<List<Order>>() {}.type
                    val orders: List<Order>? = gson.fromJson(jsonString, ordersListType)

                    if (orders != null) {
                        orders.forEach { it.setIconResId() }
                        _costcoListOfOrders.value = orders
                    }

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

                    val ordersListType = object : TypeToken<List<Order>>() {}.type
                    val orders: List<Order>? = gson.fromJson(jsonString, ordersListType)

                    if (orders != null) {
                        orders.forEach { it.setIconResId() }
                        _traderJoesListOfOrders.value = orders
                    }

                    Log.d("Firebase Data", "Trader Joes Orders: ${_traderJoesListOfOrders.value}")
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("Firebase Data", "loadPost:onCancelled", error.toException())
            }
        })
    }

    // Order selection
    private val _selectedOrders = MutableStateFlow<Set<String>>(emptySet())
    val selectedOrders: StateFlow<Set<String>> = _selectedOrders.asStateFlow()

    private val _selectedCustomerNames = MutableStateFlow<List<String>>(emptyList())
    val selectedCustomerNames: StateFlow<List<String>> = _selectedCustomerNames.asStateFlow()

    init {
        viewModelScope.launch {
            selectedOrders.collect { names ->
                _selectedCustomerNames.value = names.map { order ->
                    order.substringAfter(':')
                }
            }
        }
    }

    fun toggleOrderSelection(order: Order, storeName: String) {
        val orderId = "$storeName:${order.name}"
        _selectedOrders.update { currentSelection ->
            if (orderId in currentSelection) {
                currentSelection - orderId
            } else {
                currentSelection + orderId
            }
        }
    }

    fun clearSelection() {
        _selectedOrders.value = emptySet()
        _selectedCustomerNames.value = emptyList()
    }
}

