

// test pricers
val S = 10.0
val X = 13.0
val R = 0.05
val Sig = 0.3
val T = 1
println("blackscholes call = " + new BlackScholesCall(S,X,R,Sig,T).eval)
println("montecarlo call = " + new MonteCarloCall(S,X,R,Sig,T,50000).eval)

