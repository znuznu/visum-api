package helpers.mappers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.text.SimpleDateFormat;

public class TestMapper {
  public static String toJsonString(final Object obj) {
    try {
      ObjectMapper object = new ObjectMapper();
      object.registerModule(new JavaTimeModule());
      object.setDateFormat(new SimpleDateFormat());

      return object.writeValueAsString(obj);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
