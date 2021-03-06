/* ====================================================================
   Licensed to the Apache Software Foundation (ASF) under one or more
   contributor license agreements.  See the NOTICE file distributed with
   this work for additional information regarding copyright ownership.
   The ASF licenses this file to You under the Apache License, Version 2.0
   (the "License"); you may not use this file except in compliance with
   the License.  You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
==================================================================== */

package org.apache.poi.hdgf;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayInputStream;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

public final class TestHDGFLZW {
    public static final byte[] testTrailerComp = {
            123,      // *mask bit* 1,2,4-7
            -60, 2,
            -21, -16, // 3 @ 4093
            1, 0, 0, -72,
            -13, -16, // 3 @ 5
            78,       // *mask bit* 2,3,4,7
            -32, -5,  // 14 @ 4082
            1, 0, 3,
            -21, -16, // 3 @ 4093
            10, 5,    // 8 @ 28
            4,
            -21, -16, // 3 @ 4093
            21,       // *mask bit* 1,3,5
            9,
            -21, -16, // 3 @ 4093
            103,
            -21, -16, // 3 @ 4093
            34,
            -36, -1,  // 18 @ 4078
            52, 15,   // 18 @ 70
            70, 15,   // 18 @ 88
            120,      // *mask bit*
            88, 15,   // 18 @ 106
            -7, -2,   // 17 @ 11
            -28, -9,  // 10 @ 4086
            -123, 21, 0, 44,
            -122, 1,  // 4 @ 152
            -4,       // *mask bit*
            104, 15,  // 18 @ 122
            -24, -13, 40, -98, 32,
            78, 102, -67, -1, -2, -30, 64, 40, -67, -113, -73, 116, -98,
            -85, 2, 66, 123, 9, 109, -85, 2, -89, 14, -56, -69, -83, -79,
            -34, -3, 120, 110, 75, -9, -10, 20, -6, -25, -12, 22, -21, -16,
            -12, -81, 67, 1, -128, -70, -21, -16, 84, -21, -16, 70, 0, 23,
            -21, -16, 76, 47, -40, 79, 1, -44, -21, -16, 32, 3, 18, 12, 17,
            -43, -68, 17, 16, -8, 21, 22, -1, -21, -16, -84, -1, -35, 79,
            -9, -10, 96, 0, 46, -21, -16, 44, -39, -41, 79, 1, 119, -13,
            -16, -106, -13, -16, 84, 0, 125, 26, -21, -16, 68, -38, 79, 1,
            17, 10, 0, -97, 50, 10, 0, 0, -42, -108, 15, 118, 31, 0, -3, 29,
            -21, -16, -100, -25, 79, 1, -18, 97, -36, 76, 16, -21, -16, 86,
            0, 36, -5, 1, -5, 79, 63, 1, -124, 98, 0, 0, 28, 3, 20, -34, -3,
            125, 33, -21, -16, 100, -4, 79, 1, -92, -91, 16, -22, 24, 19, 41,
            -21, -16, -44, -59, 16, 108, 100, 0, -21, 0, 71, -105, 18, 39, 85,
            17, -3, 79, 1, 95, -108, 113, 0, 0, 104, 3, 18, 49, 49, 17, -1, 64,
            85, 1, 0, 114, 0, 0, -93, -36, -21, -16, 100, 31, 0, 0, -40, -21,
            -16, -92, 66, 127, 85, 1, 98, 119, 0, 0, -48, 79, 18, -3, 50, -17,
            1, 67, 85, 1, 81, -127, 0, -41, 0, 14, 6, 4, 17, 63, -63, 17, 68,
            85, -65, 1, 30, -120, 0, 0, 42, 79, 18, 68, 126, -21, -16, -76, 69,
            85, 1, 102, -119, 72, 37, 0, 97, 33};
    public static final byte[] testTrailerDecomp = {
            -60, 2, 0, 0, 0, 1, 0, 0, -72, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 1, 0, 3, 0, 0, 0, 3, 0, 0, 0, 3, 0, 0, 0, 4, 0, 0,
            0, 9, 0, 0, 0, 103, 0, 0, 0, 34, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            -123, 21, 0, 44, -123, 21, 0, 44, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 40, -98, 32, 78, 102, -67,
            -2, -30, 64, 40, -67, -113, -73, 116, -67, -2, -30, 64, 40, 66,
            123, 9, 109, -67, -2, -30, 64, 40, -98, 32, 78, 102, -67, -2, -30,
            64, 40, -67, -113, -73, 116, -67, -2, -30, 64, -56, -83, -79, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 120, 110, 75, 1, 0, 0, 0,
            0, 0, 0, 0, 0, 20, 0, 0, 0, 0, 0, 0, 0, 22, 0, 0, 0, -12, -81, 67,
            1, -128, 0, 0, 0, 84, 0, 0, 0, 70, 0, 23, 0, 0, 0, 76, -40, 79, 1,
            -44, 0, 0, 0, 32, 0, 0, 0, 84, 0, 23, 0, 0, 0, -68, -40, 79, 1, -8,
            0, 0, 0, 32, 0, 0, 0, 84, 0, -1, 0, 0, 0, -84, -1, 79, 1, 0, 0, 0,
            0, 0, 0, 0, 0, 96, 0, 46, 0, 0, 0, 44, -39, 79, 1, 119, 1, 0, 0,
            -106, 1, 0, 0, 84, 0, 26, 0, 0, 0, 68, -38, 79, 1, 17, 3, 0, 0,
            50, 10, 0, 0, -42, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            29, 0, 0, 0, -100, -25, 79, 1, -18, 97, 0, 0, -106, 0, 0, 0, 86, 0,
            36, 0, 0, 0, -12, -5, 79, 1, -124, 98, 0, 0, 28, 0, 0, 0, 84, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 33, 0, 0, 0, 100,
            -4, 79, 1, -92, 98, 0, 0, 32, 0, 0, 0, 84, 0, 41, 0, 0, 0, -44, -4,
            79, 1, 108, 100, 0, 0, 71, 0, 0, 0, 86, 0, 39, 0, 0, 0, 68, -3, 79,
            1, -108, 113, 0, 0, 104, 0, 0, 0, 84, 0, 49, 0, 0, 0, -84, 64, 85,
            1, 0, 114, 0, 0, -93, 0, 0, 0, -42, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, -40, 0, 0, 0, -92, 66, 85, 1, 98, 119,
            0, 0, -48, 1, 0, 0, 84, 0, 50, 0, 0, 0, 20, 67, 85, 1, 81, -127,
            0, 0, 14, 6, 0, 0, 84, 0, 63, 0, 0, 0, 100, 68, 85, 1, 30, -120,
            0, 0, 42, 1, 0, 0, 84, 0, 68, 0, 0, 0, -76, 69, 85, 1, 102, -119,
            0, 0, 42, 1, 0, 0, 84, 0, 0, 0, 0, 0
    };

    @Test
    void testCounts() throws Exception {
        assertEquals(339, testTrailerComp.length);
        assertEquals(632, testTrailerDecomp.length);

        // decompress it using our engine
        HDGFLZW lzw = new HDGFLZW();
        byte[] dec = lzw.decompress(new ByteArrayInputStream(testTrailerComp));

        // Check it's of the right size
        assertEquals(632, dec.length);

/*
		// Encode it again using our engine
		byte[] comp = lzw.compress(new ByteArrayInputStream(testTrailerDecomp));

		// Check it's of the right size
		assertEquals(339, comp.length);
*/
    }

    @Test
    void testDecompress() throws Exception {
        assertEquals(339, testTrailerComp.length);
        assertEquals(632, testTrailerDecomp.length);

        // decompress it using our engine
        HDGFLZW lzw = new HDGFLZW();
        byte[] dec = lzw.decompress(new ByteArrayInputStream(testTrailerComp));

        // Now check it's the right data
        assertArrayEquals(testTrailerDecomp, dec);
    }

    /**
     * Test that we can round-trip a little bit.
     * Uses a part short enough that we agree with visio
     * on the best way to compress it
     */
    @Test
    void testCompressMini() throws Exception {
        // first 11 bytes compressed = 12 bytes uncompressed
        byte[] sourceDecomp = Arrays.copyOf(testTrailerDecomp, 12);

        // Compress it using our engine
        HDGFLZW lzw = new HDGFLZW();
        byte[] comp = lzw.compress(new ByteArrayInputStream(sourceDecomp));

        // Now decompress it again
        byte[] decomp = lzw.decompress(new ByteArrayInputStream(comp));

        // First up, check the round tripping
		assertEquals(12, decomp.length);
		assertArrayEquals(Arrays.copyOfRange(testTrailerDecomp, 0, decomp.length), decomp);

        // Now check the compressed intermediate version
        assertEquals(11, comp.length);
		assertArrayEquals(Arrays.copyOfRange(testTrailerComp, 0, comp.length), comp);
    }

    /**
     * Tests that we can do several mask pages
     */
    @Test
    void testCompressMidi() throws Exception {
        // First 12 -> 11
        // Next 32 -> 13
        byte[] sourceDecomp = Arrays.copyOf(testTrailerDecomp, 44);

        // Compress it using our engine
        HDGFLZW lzw = new HDGFLZW();
        byte[] comp = lzw.compress(new ByteArrayInputStream(sourceDecomp));

        assertEquals(24, comp.length);

        // Now decompress it again
        byte[] decomp = lzw.decompress(new ByteArrayInputStream(comp));

        // We can only check the round-tripping, as for now
        //  visio cheats on re-using a block
		assertArrayEquals(sourceDecomp, decomp);
    }

    @Test
    void testCompressFull() throws Exception {
        assertEquals(339, testTrailerComp.length);
        assertEquals(632, testTrailerDecomp.length);

        HDGFLZW lzw = new HDGFLZW();
        byte[] decomp2 = lzw.decompress(new ByteArrayInputStream(testTrailerComp));
        assertArrayEquals(testTrailerDecomp, decomp2);


        // Compress it using our engine
        byte[] comp = lzw.compress(new ByteArrayInputStream(testTrailerDecomp));

        // the compressed binary differs, as the run length searching finds different results
        // but the decompressed data is the same

        // Now decompress it again
        byte[] decomp = lzw.decompress(new ByteArrayInputStream(comp));

        assertArrayEquals(testTrailerDecomp, decomp);
    }
}
