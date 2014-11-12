package com.mediafire.sdk.transcode.responses;

/**
 * Created by Chris on 11/12/2014.
 */
public class TranscodeResponse {
    private String schema_version;

    public String getSchemaVersion() {
        return schema_version;
    }
    // exists=status
//    <response>
//    <tftp_progress>ready</tftp_progress>
//    <video_width>854</video_width>
//    <video_height>480</video_height>
//    <video_bitrate>2500000</video_bitrate>
//    <audio_bitrate>128000</audio_bitrate>
//    <duration>232</duration>
//    <original_filesize>0</original_filesize>
//    <transcoded_filesize>61495410</transcoded_filesize>
//    <status>ready</status>
//    <schema_version>3</schema_version>
//    </response>

    // exists=check
//    <response>
//    <duration>232</duration>
//    <state>true</state>
//    <schema_version>3</schema_version>
//    </response>

    // exists=check
//    <response>
//    <state>false</state>
//    <duration>false</duration>
//    </response>

    // exists=create
//    <response>
//    <duration>232</duration>
//    <state>true</state>
//    <schema_version>3</schema_version>
//    </response>

}
