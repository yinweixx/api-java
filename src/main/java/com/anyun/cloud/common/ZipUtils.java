/*
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.anyun.cloud.common;

import java.io.*;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * @author TwitchGG <ray@proxzone.com>
 * @since 1.0.0 on 9/2/16
 */
public class ZipUtils {
    private static final int BUFFER = 2048;

    /**
     * @param os
     * @param files
     * @throws IOException
     */
    public static void zip(OutputStream os, List<File> files) throws IOException {
        BufferedInputStream origin = null;
        ZipOutputStream zos = new ZipOutputStream(new
                BufferedOutputStream(os));
        byte data[] = new byte[BUFFER];

        for (File file : files) {
            FileInputStream fi = new FileInputStream(file);
            origin = new BufferedInputStream(fi, BUFFER);
            ZipEntry entry = new ZipEntry(file.getName());
            zos.putNextEntry(entry);
            int count;
            while ((count = origin.read(data, 0, BUFFER)) != -1) {
                zos.write(data, 0, count);
            }
            origin.close();
        }
        zos.close();
    }

    /**
     * @param outputFile
     * @param files
     * @throws IOException
     */
    public static void zip(File outputFile, List<File> files) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(outputFile);
        zip(fileOutputStream, files);
    }

    /**
     * @param zis
     * @return
     * @throws IOException
     */
    public static byte[] readZipEntryData(ZipInputStream zis) throws IOException {
        int count;
        byte data[] = new byte[BUFFER];
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        BufferedOutputStream dest = new
                BufferedOutputStream(byteArrayOutputStream, BUFFER);
        while ((count = zis.read(data, 0, BUFFER)) != -1) {
            dest.write(data, 0, count);
        }
        dest.flush();
        dest.close();
        return byteArrayOutputStream.toByteArray();
    }

    /**
     * @param zipFileName
     * @param destDir
     * @throws IOException
     */
    public static void extractZipData(String zipFileName, File destDir) throws Exception {
        ZipFile zipFile = new ZipFile(zipFileName);
        Enumeration<? extends ZipEntry> zipEntryEnumeration = zipFile.entries();
        while (zipEntryEnumeration.hasMoreElements()) {
            int count;
            byte data[] = new byte[BUFFER];
            ZipEntry entry = zipEntryEnumeration.nextElement();
            String path = destDir.getAbsolutePath() + "/" + entry.getName();
            if (entry.isDirectory())
                FileUtil.mkdir(path, false);
            else {
                FileOutputStream fileOutputStream = new FileOutputStream(path);
                BufferedOutputStream dest = new
                        BufferedOutputStream(fileOutputStream, BUFFER);
                InputStream inputStream = zipFile.getInputStream(entry);
                BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
                while ((count = bufferedInputStream.read(data, 0, BUFFER)) != -1) {
                    dest.write(data, 0, count);
                }
                dest.flush();
                dest.close();
            }
        }
    }

    /**
     * @param os
     * @param bytesEntries
     * @throws IOException
     */
    public static void zipBytes(OutputStream os, List<ByteZipEntry> bytesEntries) throws IOException {
        if (bytesEntries == null || bytesEntries.isEmpty())
            return;
        ZipOutputStream zos = new ZipOutputStream(new
                BufferedOutputStream(os));
        for (ByteZipEntry byteZipEntry : bytesEntries) {
            ZipEntry entry = new ZipEntry(byteZipEntry.getName());
            entry.setSize(byteZipEntry.getContent().length);
            zos.putNextEntry(entry);
            zos.write(byteZipEntry.getContent());
            zos.closeEntry();
        }
        zos.close();
    }

    public static class ByteZipEntry {
        private byte[] content;
        private String name;

        public ByteZipEntry(byte[] content, String name) {
            this.content = content;
            this.name = name;
        }

        public byte[] getContent() {
            return content;
        }

        public String getName() {
            return name;
        }
    }
}
