package com.devstudio.forexFusion.data.firebase.repository

import com.devstudio.forexFusion.data.firebase.dbNodes.FirebaseNodes
import com.devstudio.forexFusion.data.model.CryptoSignal
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class CryptoRepository {

    /**
    firebase realtime database instance
     **/
    private val database by lazy { FirebaseDatabase.getInstance().reference }

    /**
    firebase realtime database reference
     **/
    private val reference = database.child(FirebaseNodes.Crypto.node)


    /**
    insert the signal to the firebase database
     **/
    suspend fun insert(
        signal: CryptoSignal,
        onSuccess: (String) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        val signalId = reference.push().key // Generate a unique key

        if (signalId != null) {
            val signalWithId = signal.copy(id = "$signalId")
            reference.child(signalId).setValue(signalWithId)
                .addOnSuccessListener {
                    onSuccess("Done")
                }
                .addOnFailureListener {
                    onFailure(it)
                }
        }
    }

    /**
    get all the signals from the firebase realtime database
     */
    suspend fun get(onSuccess: (List<CryptoSignal>) -> Unit, onFailure: (Exception) -> Unit) {
        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val signals = mutableListOf<CryptoSignal>()
                snapshot.children.forEach {
                    val signal = it.getValue(CryptoSignal::class.java)
                    if (signal != null) {
                        signals.add(signal)
                    }
                }
                onSuccess(signals)
            }

            override fun onCancelled(error: DatabaseError) {
                onFailure(error.toException())
            }
        })
    }

    /**
    update the signal in the firebase realtime database
     */
    suspend fun update(
        signal: CryptoSignal,
        onSuccess: (String) -> Unit,
        onFailure: (Exception) -> Unit
    ) {

        reference.child(signal.id).setValue(signal)
            .addOnSuccessListener {
                onSuccess("Done")
            }
            .addOnFailureListener {
                onFailure(it)
            }
    }

    /**
    delete the signal from the firebase realtime database
     */
    suspend fun delete(signalId: String, onSuccess: (String) -> Unit, onFailure: (Exception) -> Unit) {

        reference.child(signalId).removeValue()
            .addOnSuccessListener {
                onSuccess("Done")
            }
            .addOnFailureListener {
                onFailure(it)
            }
    }


}