package rosd_messages;

public interface RosDRequest extends org.ros.internal.message.Message {
  static final java.lang.String _TYPE = "rosd_messages/RosDRequest";
  static final java.lang.String _DEFINITION = "string task\n";
  java.lang.String getTask();
  void setTask(java.lang.String value);
}
