import scala.compiletime.ops.boolean
class Payment(
    var id: String,
    val amount: Int,
    val agent: String = "",
    val membershipId: String = ""
) {
  var components: List[Component] = List[Component]()

  def processAllComponents(
      processableComponents: List[Component]
  ): Boolean = {
    println()
    println("Processing payment id: " + id)
    processableComponents.map(_.processComponent()).reduce((x, y) => x && y)
  }

  def addComponents(new_components: List[Component]): Boolean = {
    components = new_components ++ components
    return true
  }
}

trait Component() {
  var payment: Payment

  def processComponent(): Boolean
}

abstract class Membership(membershipId: String) {
  def notifyMember(noticeReason: String): Boolean =
    val strMsg: String =
      "E-mail sent to member " + membershipId + " notifying them of their membership " + noticeReason
    println(strMsg)
    return true
}

class Physical(var payment: Payment) extends Component {
  def processComponent(): Boolean = {
    val strMsg: String = "Generated packing slip for payment " + payment.id
    val strMsgComm: String =
      "Generated commission payment to agent " + payment.agent
    println(strMsg)
    println(strMsgComm)

    return true
  }
}

class Book(var payment: Payment) extends Component {
  def processComponent(): Boolean = {
    val strMsg: String =
      "Generated royalty packing slip for payment " + payment.id
    println(strMsg)
    return true
  }
}

class Video(var payment: Payment) extends Component {
  def processComponent(): Boolean = {
    return true
  }
}

class MembershipUpgrade(var payment: Payment, membershipId: String)
    extends Membership(membershipId),
      Component {
  def processComponent(): Boolean = {
    val strMsg: String =
      "Upgraded membership " + payment.membershipId
    println(strMsg)
    super.notifyMember("upgrade")
    return true
  }
}

class MembershipActivation(var payment: Payment, membershipId: String)
    extends Membership(membershipId),
      Component {
  def processComponent(): Boolean = {
    val strMsg: String =
      "Activated membership " + payment.membershipId
    println(strMsg)
    super.notifyMember("activation")
    return true
  }
}

class Learn2Ski(var payment: Payment) extends Component {
  def processComponent(): Boolean = {
    val strMsg: String =
      "Added product: First Aid to packing slip for payment" + payment.id
    println(strMsg)
    return true
  }
}

class PaymentHandler() {

  var payments: List[Payment] = List[Payment]()
  var processedPayments: List[(String, Boolean)] = List[(String, Boolean)]()

  def addPayment(payment: Payment): Boolean = {
    val new_payments: List[Payment] = payment :: payments
    payments = new_payments
    return true
  }

  def addPayments(payment: List[Payment]): Boolean = {
    val new_payments: List[Payment] = payment ++ payments
    payments = new_payments
    return true
  }

  def processPayments(): List[(String, Boolean)] = {
    processedPayments =
      payments.map(e => (e.id, e.processAllComponents(e.components)))
    return processedPayments
  }
}

@main def main(): Unit =
  val payment1 = new Payment("01", 59, "Steve")
  val payment1Comps = List(new Physical(payment1))
  payment1.addComponents(payment1Comps)
  val payment2 = new Payment("02", 200, "Greta")
  val payment2Comps = List(new Physical(payment2), new Book(payment2))
  payment2.addComponents(payment2Comps)
  val payment3 = new Payment("03", 220, "Greta", "M01")
  val payment3Comps = List(
    new MembershipActivation(payment3, payment3.membershipId)
  )
  payment3.addComponents(payment3Comps)
  val payment4 = new Payment("04", 100, "Jane", "M01")
  val payment4Comps = List(
    new MembershipUpgrade(payment4, payment4.membershipId)
  )
  payment4.addComponents(payment4Comps)
  val payment5 = new Payment("05", 200, "Jane")
  val payment5Comps =
    List(new Physical(payment5), new Video(payment5), new Learn2Ski(payment5))
  val paymentTester = new PaymentHandler()
  payment5.addComponents(payment5Comps)
  paymentTester.addPayments(
    List(payment1, payment2, payment3, payment4, payment5)
  )
  println(paymentTester.processPayments())
