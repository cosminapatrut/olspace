package com.orange.olsnasa.globals

import android.app.Application
import com.orange.olsnasa.instances.Instances


// Instances
val app: Application = Instances.koinApp.koin.get()