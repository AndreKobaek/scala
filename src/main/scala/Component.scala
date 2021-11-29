// class Payment(var id: String) extends Object {
//   def processAllComponents(
//       processableComponents: List[Component]
//   ): List[Boolean] = {
//     processableComponents.map(_.processComponent())
//   }
// }

// trait Component(var payment: Payment) {

//   def processComponent(): Boolean
// }

// class Physical(payment: Payment) extends Object, Component(payment: Payment) {
//   def processComponent(): Boolean = {
//     println(s"Generated packing slip for payment $payment.id")

//     return true
//   }
// }

// @main def main(): Unit =
//   val paytest = new Payment("02")
//   val comps = List(new Physical(paytest), new Physical(paytest))
