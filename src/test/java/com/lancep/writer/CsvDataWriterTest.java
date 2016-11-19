package com.lancep.writer;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;
import java.io.OutputStream;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.junit.MatcherAssert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class CsvDataWriterTest {

    CsvDataWriter CsvDataWriter;
    @Mock OutputStream outputStream;

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