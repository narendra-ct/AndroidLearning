package com.example.kortlinsampleform.Modal

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable
import java.math.BigDecimal
import java.util.ArrayList

public class NonCuratedItem: Serializable {

    var itemName: String? = null
    var link: String? = null
    var price: BigDecimal? = null
    var itemDescription: String? = null
    var itemSize: Int? = null
    var images: ArrayList<String>? = null
}