import org.apache.commons.math3.distribution.{LogNormalDistribution, AbstractRealDistribution, NormalDistribution}

/**
  * Created by julienmonnier on 17/05/2016.
  */



trait CallOption {
  val spot, strike, interest, sigma, timeToMaturity: Double
  protected var dist: AbstractRealDistribution = new NormalDistribution()
  def eval: Double
}

class BlackScholesCall(override val spot: Double, override val strike: Double, override val interest: Double,
                       override val sigma: Double, override val timeToMaturity: Double) extends CallOption {

  override def eval: Double = {
    val d1 = (math.log(spot/strike) + ((interest + math.pow(sigma,2)/2)*timeToMaturity)) / (sigma * math.sqrt(timeToMaturity))
    val d2 = d1 - sigma * math.sqrt(timeToMaturity)
    spot * dist.cumulativeProbability(d1) - strike * math.exp(-interest * timeToMaturity) * dist.cumulativeProbability(d2)
  }
}

class MonteCarloCall(override val spot: Double, override val strike: Double, override val interest: Double,
                     override val sigma: Double, override val timeToMaturity: Double, val N:Int = 10000) extends CallOption {

  override def eval: Double = {
    val accr = math.exp(-interest * timeToMaturity)

    val mean = (1 to N).par.aggregate(0.0)((sumPrice,y)=>{
      val eps = dist.sample()
      val price = spot * math.exp(-0.5 * math.pow(sigma, 2) * timeToMaturity + sigma * eps * math.sqrt(timeToMaturity))
      val payoff = math.max(price - (strike * accr), 0)
      sumPrice + payoff
    },(_+_))/ N
    mean
  }
}

