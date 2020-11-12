package dev.all4.versionUp.utils.extentions

import android.view.View
import android.widget.Toast

fun View.toast(msg: String) = Toast.makeText(context, msg, Toast.LENGTH_LONG).show()

fun View.setGone() { this.visibility = View.GONE }
fun View.setVisible() { this.visibility = View.VISIBLE }
fun View.setInvisible() { this.visibility = View.INVISIBLE }