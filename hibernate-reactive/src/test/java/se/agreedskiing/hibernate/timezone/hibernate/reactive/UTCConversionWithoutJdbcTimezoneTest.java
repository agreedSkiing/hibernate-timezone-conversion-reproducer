package se.agreedskiing.hibernate.timezone.hibernate.reactive;

import io.smallrye.mutiny.helpers.test.UniAssertSubscriber;
import java.util.TimeZone;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UTCConversionWithoutJdbcTimezoneTest extends PostgresContainer {

  Application repository;

  static final String PERSISTANCE_UNIT = "no.time.zone";
  static final int PORT = PostgresContainer.DATABASE.getFirstMappedPort();

  @BeforeAll
  static void boot() {
    TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
  }

  @BeforeEach
  void start() {
    this.repository = new Application();
  }

  @Test
  void getting_as_instants() {
    this.repository.getAsInstants(
        UTCConversionWithoutJdbcTimezoneTest.PERSISTANCE_UNIT,
        UTCConversionWithoutJdbcTimezoneTest.PORT
      )
      .invoke(entity ->
        Assertions.assertAll(
          () ->
            Assertions.assertEquals(
              "2022-12-18T19:39:20Z",
              entity.noTimeZone.toString(),
              ErrorTexts.NO_TIMEZONE_FIELD_FAILED.explenation
            ),
          () ->
            Assertions.assertEquals(
              "2022-12-18T18:39:20Z",
              entity.withTimeZone.toString(),
              ErrorTexts.TIMEZONE_FIELD_FAILED.explenation
            ),
          () ->
            Assertions.assertEquals(
              "2022-12-18T17:39:20Z",
              entity.withTimeZoneGmt2.toString(),
              ErrorTexts.TIMEZONE_WITH_GMT_2_FIELD_FAILED.explenation
            )
        )
      )
      .subscribe()
      .withSubscriber(UniAssertSubscriber.create())
      .awaitItem()
      .assertCompleted();
  }

  @Test
  void getting_as_local_date_time() {
    this.repository.getAsLocalDateTimes(
        UTCConversionWithoutJdbcTimezoneTest.PERSISTANCE_UNIT,
        UTCConversionWithoutJdbcTimezoneTest.PORT
      )
      .invoke(entity ->
        Assertions.assertAll(
          () ->
            Assertions.assertEquals(
              "2022-12-18T19:39:20",
              entity.noTimeZone.toString(),
              ErrorTexts.NO_TIMEZONE_FIELD_FAILED.explenation
            ),
          () ->
            Assertions.assertEquals(
              "2022-12-18T18:39:20",
              entity.withTimeZone.toString(),
              ErrorTexts.TIMEZONE_FIELD_FAILED.explenation
            ),
          () ->
            Assertions.assertEquals(
              "2022-12-18T17:39:20",
              entity.withTimeZoneGmt2.toString(),
              ErrorTexts.TIMEZONE_WITH_GMT_2_FIELD_FAILED.explenation
            )
        )
      )
      .subscribe()
      .withSubscriber(UniAssertSubscriber.create())
      .awaitItem()
      .assertCompleted();
  }

  @Test
  void getting_as_offset_date_time() {
    this.repository.getAsOffsetDateTimes(
        UTCConversionWithoutJdbcTimezoneTest.PERSISTANCE_UNIT,
        UTCConversionWithoutJdbcTimezoneTest.PORT
      )
      .invoke(entity ->
        Assertions.assertAll(
          () ->
            Assertions.assertEquals(
              "2022-12-18T19:39:20Z",
              entity.noTimeZone.toString(),
              ErrorTexts.NO_TIMEZONE_FIELD_FAILED.explenation
            ),
          () ->
            Assertions.assertEquals(
              "2022-12-18T18:39:20Z",
              entity.withTimeZone.toString(),
              ErrorTexts.TIMEZONE_FIELD_FAILED.explenation
            ),
          () ->
            Assertions.assertEquals(
              "2022-12-18T17:39:20Z",
              entity.withTimeZoneGmt2.toString(),
              ErrorTexts.TIMEZONE_WITH_GMT_2_FIELD_FAILED.explenation
            )
        )
      )
      .subscribe()
      .withSubscriber(UniAssertSubscriber.create())
      .awaitItem()
      .assertCompleted();
  }

  @Test
  void getting_as_zoned_date_time() {
    this.repository.getAsZonedDateTimes(
        UTCConversionWithoutJdbcTimezoneTest.PERSISTANCE_UNIT,
        UTCConversionWithoutJdbcTimezoneTest.PORT
      )
      .invoke(entity ->
        Assertions.assertAll(
          () ->
            Assertions.assertEquals(
              "2022-12-18T19:39:20Z[UTC]",
              entity.noTimeZone.toString(),
              ErrorTexts.NO_TIMEZONE_FIELD_FAILED.explenation
            ),
          () ->
            Assertions.assertEquals(
              "2022-12-18T18:39:20Z[UTC]",
              entity.withTimeZone.toString(),
              ErrorTexts.TIMEZONE_FIELD_FAILED.explenation
            ),
          () ->
            Assertions.assertEquals(
              "2022-12-18T17:39:20Z[UTC]",
              entity.withTimeZoneGmt2.toString(),
              ErrorTexts.TIMEZONE_WITH_GMT_2_FIELD_FAILED.explenation
            )
        )
      )
      .subscribe()
      .withSubscriber(UniAssertSubscriber.create())
      .awaitItem()
      .assertCompleted();
  }
}
