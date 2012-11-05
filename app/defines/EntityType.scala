package defines

object EntityType extends Enumeration() {
  type Type = Value
  val DocumentaryUnit = Value("documentaryUnit")
  val Agent = Value("agent")
  val Action = Value("action")
  val UserProfile = Value("userProfile")
  val Group = Value("group")
  val ContentType = Value("contentType")
  val DocumentaryUnitDescription = Value("documentDescription")
  val AgentDescription = Value("agentDescription")
  val AuthorityDescription = Value("authorityDescription")
}