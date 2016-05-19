import org.apache.commons.math3.distribution.{NormalDistribution, AbstractRealDistribution}

// test pricers
val S = 10.0
val X = 20.0
val R = 0.05
val Sig = 0.3
val T = 1
println("blackscholes call = " + new BlackScholes().priceCall(S,X,R,Sig,T))
println("montecarlo call = " + new MonteCarlo(new NormalDistribution()).priceCall(S,X,R,Sig,T))
