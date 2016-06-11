/*
 * Copyright (C) 2015 Square, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.yong.eyepetizer.lib.core.convert;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.zip.GZIPInputStream;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import retrofit2.Converter;

import static okhttp3.internal.Util.UTF_8;

final class CusGsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private final Gson gson;
    private final TypeAdapter<T> adapter;

    CusGsonResponseBodyConverter(Gson gson, TypeAdapter<T> adapter) {
        this.gson = gson;
        this.adapter = adapter;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        InputStream stream = value.byteStream();
        GZIPInputStream gzipInputStream = null;
        InputStreamReader inputStreamReader;
        // Gzip数据
        try {
            gzipInputStream = new GZIPInputStream(stream);
            inputStreamReader = new InputStreamReader(gzipInputStream);
        } catch (IOException e) {
            inputStreamReader = new InputStreamReader(stream, charset(value));
        }
        // 妈的不知道什么鬼，这里就出问题
        JsonReader jsonReader = gson.newJsonReader(inputStreamReader);
        try {
            return adapter.read(jsonReader);
        } finally {
            try {
                inputStreamReader.close();
                if (gzipInputStream != null) {
                    gzipInputStream.close();
                }
                value.close();
            } catch (Exception e) {
                // ignore
            }
        }
    }

    private Charset charset(ResponseBody value) {
        MediaType contentType = value.contentType();
        return contentType != null ? contentType.charset(UTF_8) : UTF_8;
    }

}
