package rosd_messages;

public interface Composite extends org.ros.internal.message.Message {
  static final java.lang.String _TYPE = "rosd_messages/Composite";
  static final java.lang.String _DEFINITION = "# composite message. required for testing import calculation in generators\nCompositeTask task\n";
  rosd_messages.CompositeTask getTask();
  void setTask(rosd_messages.CompositeTask value);
}
