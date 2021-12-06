// http://codekata.com/kata/kata16-business-rules/

object MembershipType extends Enumeration {
  type MembershipType = Value

  val Activation, Upgrade = Value
}

object ComponentType extends Enumeration {
  type ComponentType = Value

  val Physical, Digital = Value
}

object ComponentSubType extends Enumeration {
  type ComponentSubType = Value

  val Book, Video = Value
}

import scala.util.{Try, Success, Failure}
import MembershipType._, ComponentType._, ComponentSubType._

case class Membership(memType: MembershipType)

case class Component(
    comType: ComponentType,
    comSubType: ComponentSubType,
    title: String,
    agentId: String
)

case class Payment(
    component: Option[Component] = None,
    member: Option[Membership] = None
)

case class PaymentHandler() {
  def processPayment(payment: Payment): Try[String] = {
    (payment.component, payment.member) match {
      case (Some(component), None)  => processComponent(component)
      case (None, Some(membership)) => processMembership(membership)
      case _                        => Failure(new IllegalArgumentException)
    }
  }

  def processComponent(component: Component): Try[String] = {
    val componentResult = component.comType match {
      case Physical => Success("Pack slip + Commission")
      case _        => Success("")
    }
    val subComponentResult = (component.comSubType, component.title) match {
      case (Book, _)               => Success("Royality slip")
      case (Video, "Learn to Ski") => Success("Added First Aid")
    }
    for {
      resultComponent <- componentResult;
      resultSubComponent <- subComponentResult
    } yield (resultComponent + " + " + resultSubComponent)
  }

  def processMembership(membership: Membership): Try[String] = {
    val result = membership.memType match {
      case Activation => Success("Membership activated")
      case Upgrade    => Success("Membership upgraded")
    }
    result match {
      case Success(_) => Success("Member notified")
      // case Failure(x) => Failure(x)
    }
  }
}
object PaymentHandler {}

// val ph = PaymentHandler()
// val payment1 = Payment(Some(Component(ComponentType.Physical, ComponentSubType.Book, "SSS", "Susan")), None)
// ph.processPayment(payment1)
