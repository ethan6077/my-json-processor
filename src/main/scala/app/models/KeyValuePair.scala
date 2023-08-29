package app.models

case class KeyValuePair (key: String, value: String) {
  override def toString: String = {
    s"{$key: $value}"
  }
}
