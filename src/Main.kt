import com.yimiao.app.ext.toFloor
import com.yimiao.app.ext.toFloorAndCurrency

/**
 * Created by fengjunming_t on 2017/6/17 0017.
 */
fun main(args: Array<String>) {

    var d = 2.5
    d = d.toFloor()
    println(d.toFloorAndCurrency(0))

    println(d)
    println(Math.floor(2.5))
    println(Math.ceil(2.5))



    listOf("", "").forEachIndexed {
        it, index ->


    }


    println("this  ".removeSuffix("is"))
}