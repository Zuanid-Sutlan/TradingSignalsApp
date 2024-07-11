package com.devstudio.forexFusion

import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Error
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devstudio.forexFusion.data.firebase.repository.BrokerRepository
import com.devstudio.forexFusion.data.firebase.repository.CommunityRepository
import com.devstudio.forexFusion.data.firebase.repository.CryptoRepository
import com.devstudio.forexFusion.data.firebase.repository.EventRepository
import com.devstudio.forexFusion.data.firebase.repository.ForexRepository
import com.devstudio.forexFusion.data.firebase.repository.NotificationRepository
import com.devstudio.forexFusion.data.firebase.repository.ResultRepository
import com.devstudio.forexFusion.data.model.BrokerLink
import com.devstudio.forexFusion.data.model.CommunityLink
import com.devstudio.forexFusion.data.model.EventSignal
import com.devstudio.forexFusion.data.model.MessageBar
import com.devstudio.forexFusion.data.model.enums.Result
import com.devstudio.forexFusion.data.model.ResultSignal
import com.devstudio.forexFusion.data.model.CryptoSignal
import com.devstudio.forexFusion.data.model.ForexSignal
import com.devstudio.forexFusion.data.repository.AuthRepository
import com.devstudio.forexFusion.ui.utils.networkUtils.ConnectivityObserver
import com.devstudio.forexFusion.ui.utils.Prefs
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.onesignal.OneSignal
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    /**
    repository initialization
     */
    private val cryptoRepository = CryptoRepository()
    private val forexRepository = ForexRepository()
    private val eventRepository = EventRepository()
    private val resultRepository = ResultRepository()
    private val communityRepository = CommunityRepository()
    private val brokerRepository = BrokerRepository()
    private val notificationRepository = NotificationRepository()



    private val connectivityObserver = ConnectivityObserver(AppClass.context)
    private val _authRepository: AuthRepository

    private val googleSignInClient: GoogleSignInClient


    private val _authState = MutableLiveData<Result<Boolean>>()
    val authState: MutableLiveData<Result<Boolean>> get() = _authState


    init {
        viewModelScope.launch {
            delay(1000L)
            _isLoading.value = false
        }

        _authRepository = AuthRepository(FirebaseAuth.getInstance())

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(ContextCompat.getString(AppClass.context, R.string.web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(AppClass.context, gso)

//        observeConnectivity(AppClass.context)
//        _authState.value = authRepository.auth.currentUser != null


        // get all the crypto signals from the firebase realtime database
        getCryptoSignals()
        // get all the forex signals from the firebase realtime database
        getForexSignals()
        // get all the event signals from the firebase realtime database
        getEventSignals()
        // get all the community links from the firebase realtime database
        getCommunityLinks()
        // get all the results from the firebase realtime database
        getResults()
        // get all the broker links from the firebase realtime database
        getBrokerLinks()
        // get all the notifications from the firebase realtime database
//        getNotifications()

    }

    val firebaseUser = _authRepository.auth.currentUser


    // for setting theme...
    private var _changeTheme = mutableStateOf(Prefs.isDarkTheme)
    var theme: State<Boolean> = _changeTheme

    fun setTheme(theme: Boolean) {
        _changeTheme.value = theme
    }

    // for app bar title
    val appBarTitle = mutableStateOf("")

    fun setAppBarTitle(title: String) {
        appBarTitle.value = title
    }

    // splash screen loading
    private var _isLoading = mutableStateOf(true)
    var splashScreenIsLoading: State<Boolean> = _isLoading

    // check the internet connection is available or not
    val isConnected = connectivityObserver.isConnected



    // for managing the login signup dialog in the welcome screen
    private var _showLoginDialog = mutableStateOf(false)
    var showLoginDialog: State<Boolean> = _showLoginDialog
    fun showLoginDialog(state: Boolean) {
        _showLoginDialog.value = state
    }

    private var _showSignUpDialog = mutableStateOf(false)
    var showRegisterDialog: State<Boolean> = _showSignUpDialog
    fun showRegisterDialog(state: Boolean) {
        _showSignUpDialog.value = state
    }


    // for showing snack bar messages to the user in the app
    private var _snackBarVisible = mutableStateOf(false)
    var snackBarVisible: State<Boolean> = _snackBarVisible
    private var _snackBarMessage = mutableStateOf<MessageBar?>(null)
    var snackBarMessage: State<MessageBar?> = _snackBarMessage
    fun setUpSnackBar(state: Boolean, message: MessageBar?) {
        _snackBarVisible.value = state
        _snackBarMessage.value = message
    }
    fun snackBarDisable() {
        _snackBarVisible.value = false
        _snackBarMessage.value = null
    }


    // for register user on firebase

    fun getGoogleSignInIntent() = googleSignInClient.signInIntent

    fun firebaseAuthWithGoogle(idToken: String, onResult: (Boolean, String) -> Unit) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        _authRepository.auth.signInWithCredential(credential)
            .addOnCompleteListener{
                Log.i("mainCreds", "firebaseAuthWithGoogle: ${it.result.credential}")
                Log.i("mainToken", "firebaseAuthWithGoogle: ${idToken}")
                if (it.isSuccessful) {
                    onResult(true, "Sign-In successful")
                    it.result.user?.uid?.let { it1 -> OneSignal.login(it1) }
                } else {
                    onResult(false, it.exception?.message ?: "Error signing in")
                }
            }
    }

    fun signOut() {
        viewModelScope.launch {
            _authState.value = _authRepository.signOut()
        }
    }


    /**
    Signals Lists
     */
    private val _cryptoSignals = mutableStateOf<List<CryptoSignal>>(emptyList())
    val cryptoSignals: State<List<CryptoSignal>> = _cryptoSignals

    private val _forexSignals = mutableStateOf<List<ForexSignal>>(emptyList())
    val forexSignals: State<List<ForexSignal>> = _forexSignals

    private val _eventSignals = mutableStateOf<List<EventSignal>>(emptyList())
    val eventSignals: State<List<EventSignal>> = _eventSignals

    private val _communityLinks = mutableStateOf<List<CommunityLink>>(emptyList())
    val communityLinks: State<List<CommunityLink>> = _communityLinks

    private val _result = mutableStateOf<List<ResultSignal>>(emptyList())
    val results: State<List<ResultSignal>> = _result

    private val _brokerLinks = mutableStateOf<List<BrokerLink>>(emptyList())
    val brokerLinks: State<List<BrokerLink>> = _brokerLinks


    /**
    crypto signals CRUD operations
     */
    private fun getCryptoSignals() {
        viewModelScope.launch {
            cryptoRepository.get(
                onSuccess = {
                    _cryptoSignals.value = it
                },
                onFailure = {
                    setUpSnackBar(
                        state = true,
                        message = MessageBar(
                            text = it.message ?: "Something went wrong",
                            icon = Icons.Rounded.Error,
                            color = Color.Red
                        )
                    )
                }
            )
        }
    }

    /**
     * forex signals CRUD operations
     * */
    private fun getForexSignals() {
        viewModelScope.launch {
            forexRepository.get(
                onSuccess = {
                    _forexSignals.value = it
                },
                onFailure = {
                    setUpSnackBar(
                        state = true,
                        message = MessageBar(
                            text = it.message ?: "Something went wrong",
                            icon = Icons.Rounded.Error,
                            color = Color.Red
                        )
                    )
                }
            )
        }
    }

    /**
     * event signals CRUD operations
     * */
    private fun getEventSignals() {
        viewModelScope.launch {
            eventRepository.get(
                onSuccess = {
                    _eventSignals.value = it
                },
                onFailure = {
                    setUpSnackBar(
                        state = true,
                        message = MessageBar(
                            text = it.message ?: "Something went wrong",
                            icon = Icons.Rounded.Error,
                            color = Color.Red
                        )
                    )
                }
            )
        }
    }

    /**
     * Community links CRUD operations
     * */
    private fun getCommunityLinks() {
        viewModelScope.launch {
            communityRepository.get(
                onSuccess = {
                    _communityLinks.value = it
                },
                onFailure = {
                    setUpSnackBar(
                        state = true,
                        message = MessageBar(
                            text = it.message ?: "Something went wrong",
                            icon = Icons.Rounded.Error,
                            color = Color.Red
                        )
                    )
                }
            )
        }
    }

    /**
    Results CRUD operations
     **/
    private fun getResults() {
        viewModelScope.launch {
            resultRepository.get(
                onSuccess = {
                    _result.value = it
                },
                onFailure = {
                    setUpSnackBar(
                        state = true,
                        message = MessageBar(
                            text = it.message ?: "Something went wrong",
                            icon = Icons.Rounded.Error,
                            color = Color.Red
                        )
                    )
                }
            )
        }
    }

    /** Broker link CRUD operation */
    private fun getBrokerLinks() {
        viewModelScope.launch {
            brokerRepository.get(
                onSuccess = {
                    _brokerLinks.value = it
                },
                onFailure = {
                    setUpSnackBar(
                        state = true,
                        message = MessageBar(
                            text = it.message ?: "Something went wrong",
                            icon = Icons.Rounded.Error,
                            color = Color.Red
                        )
                    )
                }
            )
        }
    }


    // demo results data
//    var demoResults = listOf(
//        ResultSignal("BTC/USDT", "+200.0"),
//        ResultSignal("ETH/USDT", "-25.0"),
//        ResultSignal("XAU/USD", "+20.0"),
//        ResultSignal("EUR/USD", "-22.0"),
//        ResultSignal("CAD/USD", "-10.0"),
//    )

    // demo community links
//    val communityLinks = listOf(
//        CommunityLink(
//            "Join Our WhatsApp Community",
//            "whatsapp",
//            "https://img.icons8.com/?size=100&id=16713&format=png&color=000000"
//        ),
//        CommunityLink("Join Our X Community", "x", "R.drawable.ic_launcher_background"),
//        CommunityLink(
//            "Join Our Youtube Community",
//            "youtube",
//            "https://img.icons8.com/?size=100&id=19318&format=png&color=000000"
//        ),
//    )

    // demo data for crypto signals
//    val cryptoSignals = listOf(
//        CryptoSignal(
//            "Btc/usdt",
//            "https://st3.depositphotos.com/15237386/33975/i/450/depositphotos_339750616-stock-photo-gold-bitcoin-crypto-currency-on.jpg",
//            "6/11/2024",
//            "64539.0",
//            "Long",
//            "5",
//            "3",
//            "71000.0",
//            1,
//            "80000.0",
//            0,
//            "56000",
//            0,
//            "12",
//            "24",
//            "32",
//            "56000"
//        ),
//        CryptoSignal(
//            "Btc/usdt",
//            "https://upload.wikimedia.org/wikipedia/commons/thumb/4/46/Bitcoin.svg/1200px-Bitcoin.svg.png",
//            "6/11/2024",
//            "64539.0",
//            "Short",
//            "5",
//            "3",
//            "71000.0",
//            1,
//            "80000.0",
//            1,
//            "56000",
//            0,
//            "12",
//            "24",
//            "32",
//            "56000"
//        ),
//        CryptoSignal(
//            "Btc/usdt",
//            "https://upload.wikimedia.org/wikipedia/commons/thumb/4/46/Bitcoin.svg/1200px-Bitcoin.svg.png",
//            "6/11/2024",
//            "64539.0",
//            "Long",
//            "5",
//            "3",
//            "71000.0",
//            0,
//            "80000.0",
//            0,
//            "56000",
//            0,
//            "12",
//            "24",
//            "32",
//            "56000"
//        ),
//    )

    // demo data for forex signals
//    val forexSignals = listOf(
//        CryptoSignal(
//            "xauusd",
//            "https://s3-symbol-logo.tradingview.com/metal/gold--600.png",
//            "6/14/2024",
//            "2350.0",
//            "Long",
//            "5",
//            "3",
//            "2400.0",
//            0,
//            "2460.0",
//            0,
//            "2500",
//            0,
//            "12",
//            "24",
//            "32",
//            "2300"
//        ),
//        CryptoSignal(
//            "xauusd",
//            "https://s3-symbol-logo.tradingview.com/metal/gold--600.png",
//            "6/14/2024",
//            "2350.0",
//            "short",
//            "5",
//            "3",
//            "2400.0",
//            1,
//            "2460.0",
//            1,
//            "2500",
//            0,
//            "12",
//            "24",
//            "32",
//            "2300"
//        ),
//        CryptoSignal(
//            "xauusd",
//            "https://s3-symbol-logo.tradingview.com/metal/gold--600.png",
//            "6/14/2024",
//            "2350.0",
//            "Short",
//            "5",
//            "3",
//            "2400.0",
//            1,
//            "2460.0",
//            0,
//            "2500",
//            0,
//            "12",
//            "24",
//            "32",
//            "2300"
//        )
//    )

    // dummy data for event signals
//    val eventSignals = listOf(
//        EventSignal(
//            "eruusd",
//            "https://traders-trust.com/wp-content/uploads/currency_pair_eurusd.webp",
//            "6/14/2024",
//            "9:49 AM",
//            "up",
//            "3",
//            "Quotex",
//            "1",
//            "80",
//            "2",
//            "Must"
//        ),
//        EventSignal(
//            "eruusd",
//            "https://traders-trust.com/wp-content/uploads/currency_pair_eurusd.webp",
//            "6/14/2024",
//            "9:49 PM",
//            "Down",
//            "3",
//            "Quotex",
//            "1",
//            "90",
//            "2",
//            "Optional"
//        ),
//        EventSignal(
//            "eruusd",
//            "https://traders-trust.com/wp-content/uploads/currency_pair_eurusd.webp",
//            "6/14/2024",
//            "9:49 AM",
//            "up",
//            "3",
//            "Quotex",
//            "1",
//            "30",
//            "",
//            ""
//        ),
//
//        )


}