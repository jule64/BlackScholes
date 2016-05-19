import org.apache.commons.math3.distribution.{AbstractRealDistribution, NormalDistribution}

/**
  * Created by julienmonnier on 17/05/2016.
  */



trait OptionContract {

  val dist: AbstractRealDistribution
  def priceCall[T <: Double](spot: T, strike: T, interest: T, sigma: T, timeToMaturity: T): Double
  def pricePut[T <: Double](spot: T, strike: T, interest: T, sigma: T, timeToMaturity: T): Double
}

class MonteCarlo(override val dist: AbstractRealDistribution = new NormalDistribution(), simulations:Int = 100000) extends OptionContract {

  def this(simulations:Int) = this(new NormalDistribution(),simulations)

  def priceCall[T <: Double](spot: T, strike: T, interest: T, sigma: T, timeToMaturity: T): Double = {
    val accr = math.exp(-interest * timeToMaturity)

    // processing simulations in parallel using par + aggregate
    val mean = (1 to simulations).par.aggregate(0.0)((sumPrice,y)=>{
      val eps = dist.sample()
      val price = spot * math.exp(-0.5 * math.pow(sigma, 2) * timeToMaturity + sigma * eps * math.sqrt(timeToMaturity))
      val payoff = math.max(price - (strike * accr), 0)
      sumPrice + payoff
    },(_+_))/ simulations
    mean
  }

  override def pricePut[T <: Double](spot: T, strike: T, interest: T, sigma: T, timeToMaturity: T): Double = ???
}

class BlackScholes(override val dist:AbstractRealDistribution = new NormalDistribution()) extends OptionContract {

  def priceCall[T <: Double](spot: T, strike: T, interest: T, sigma: T, timeToMaturity: T): Double = {
    val d1 = (math.log(spot/strike) + ((interest + math.pow(sigma,2)/2)*timeToMaturity)) / (sigma * math.sqrt(timeToMaturity))
    val d2 = d1 - sigma * math.sqrt(timeToMaturity)
    spot * dist.cumulativeProbability(d1) - strike * math.exp(-interest * timeToMaturity) * dist.cumulativeProbability(d2)
  }

  override def pricePut[T <: Double](spot: T, strike: T, interest: T, sigma: T, timeToMaturity: T): Double = ???
}