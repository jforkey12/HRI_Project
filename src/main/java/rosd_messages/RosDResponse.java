package rosd_messages;

public interface RosDResponse extends org.ros.internal.message.Message {
  static final java.lang.String _TYPE = "rosd_messages/RosDResponse";
  static final java.lang.String _DEFINITION = "int64 responseMessage";
  long getResponseMessage();
  void setResponseMessage(long value);
}
