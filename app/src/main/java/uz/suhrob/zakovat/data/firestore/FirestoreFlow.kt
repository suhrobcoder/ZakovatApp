package uz.suhrob.zakovat.data.firestore

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.Query
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

@ExperimentalCoroutinesApi
inline fun <reified T> Query.getFirestoreFlow(): Flow<List<T>> = callbackFlow {
    val subscription = addSnapshotListener { snapshot, _ ->
        snapshot?.toObjects(T::class.java)?.let {
            offer(it)
        }
    }

    awaitClose { subscription.remove() }
}

@ExperimentalCoroutinesApi
inline fun <reified T> DocumentReference.getFirestoreFlow(): Flow<T> = callbackFlow {
    val subscription = addSnapshotListener { snapshot, _ ->
        snapshot?.toObject(T::class.java)?.let {
            offer(it)
        }
    }

    awaitClose { subscription.remove() }
}

@ExperimentalCoroutinesApi
inline fun <reified T> CollectionReference.getFirestoreFlow(): Flow<List<T>> = callbackFlow {
    val subscription = addSnapshotListener { snapshot, _ ->
        snapshot?.toObjects(T::class.java)?.let {
            offer(it)
        }
    }

    awaitClose { subscription.remove() }
}
