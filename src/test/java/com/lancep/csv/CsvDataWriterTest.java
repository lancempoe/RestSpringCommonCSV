package com.lancep.csv;

import mockit.Mocked;
import mockit.integration.junit4.JMockit;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.io.OutputStream;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@SuppressWarnings({"CanBeFinal", "unused"})
@RunWith(JMockit.class)
public class CsvDataWriterTest {

    private CsvDataWriter CsvDataWriter;
    private @Mocked OutputStream outputStream;

    @Before
    public void init() throws IOException {
        CsvDataWriter = new CsvDataWriter(outputStream);
    }

    @Test
    public void shouldNotFlushEveryUnderOnNone100Records() throws Exception {
        assertThat(CsvDataWriter.shouldFlush(99), is(false));
        assertThat(CsvDataWriter.shouldFlush(101), is(false));
    }

    @Test
    public void shouldFlushEveryUnderOn100Records() throws Exception {
        assertThat(CsvDataWriter.shouldFlush(100), is(true));
        assertThat(CsvDataWriter.shouldFlush(200), is(true));
    }
}