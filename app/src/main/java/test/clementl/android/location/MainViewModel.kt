package test.clementl.android.location

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.mapbox.geojson.Point
import com.mapbox.turf.TurfConstants
import com.mapbox.turf.TurfMeasurement

class MainViewModel : ViewModel() {

    lateinit var startPoint: Point

    private val _currentPoint = MutableLiveData<Point>()
    val currentPoint: LiveData<Point>
        get() = _currentPoint

    val distanse: LiveData<Double> = Transformations.map(currentPoint) {
        if (isInitialized()) TurfMeasurement.distance(it, startPoint, TurfConstants.UNIT_METERS)
        else 0.0
    }

    fun setupStartPoint(point: Point) {
        if (!this::startPoint.isInitialized) {
            startPoint = point
        }
    }

    fun setupCurrentPoint(point: Point) {
        _currentPoint.value = point
    }

    fun isInitialized() = this::startPoint.isInitialized
}