/*Copyright 2014 Genium
 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.*/
package org.genium.appium.support.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.commons.compress.archivers.sevenz.SevenZArchiveEntry;
import org.apache.commons.compress.archivers.sevenz.SevenZFile;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;

/**
 * An I/O class to provide all functions required for any input/output
 * operation.
 *
 * @author Hassan Radi
 */
public class IO {

    /**
     * Extracts a compressed 7z file to specified output directory.
     *
     * @param fileToExtract The compressed file to extract.
     * @param outputDirectory The output directory to extract the files to.
     * @throws IOException In case of any error that happens during the extract
     * operation.
     */
    public static void extract7ZipFile(File fileToExtract, String outputDirectory) throws IOException {
        try (SevenZFile sevenZFile = new SevenZFile(fileToExtract)) {
            SevenZArchiveEntry entry = sevenZFile.getNextEntry();
            while (entry != null) {
                // create the parent directory structure if needed
                new File(outputDirectory + "/"
                        + entry.getName()).getParentFile().mkdirs();

                if (!entry.isDirectory()) {
                    try (FileOutputStream out = new FileOutputStream(outputDirectory + "/" + entry.getName())) {
                        byte[] content = new byte[(int) entry.getSize()];
                        sevenZFile.read(content, 0, content.length);
                        out.write(content);
                    }
                }
                entry = sevenZFile.getNextEntry();
            }
        }
    }

    /**
     * Extracts a compressed tar file to specified output directory.
     *
     * @param fileToExtract The compressed file to extract.
     * @param outputDirectory The output directory to extract the files to.
     * @throws IOException In case of any error that happens during the extract
     * operation.
     */
    public static void extractTarFile(File fileToExtract, String outputDirectory) throws IOException {
        try (TarArchiveInputStream tarFile = new TarArchiveInputStream(
                new FileInputStream(fileToExtract))) {
            TarArchiveEntry entry = tarFile.getNextTarEntry();
            while (entry != null) {
                // create the parent directory structure if needed
                new File(outputDirectory + "/"
                        + entry.getName()).getParentFile().mkdirs();

                if (!entry.isDirectory()) {
                    try (FileOutputStream out = new FileOutputStream(outputDirectory + "/" + entry.getName())) {
                        byte[] content = new byte[(int) entry.getSize()];
                        tarFile.read(content, 0, content.length);
                        out.write(content);
                    }
                }
                entry = tarFile.getNextTarEntry();
            }
        }
    }
}
