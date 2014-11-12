package com.mediafire.sdk.transcode.responses;

/**
 * Created by Chris on 11/12/2014.
 */
public class StatusResponse extends TranscodeResponse {
    private String tftp_progress;
    private String video_width;
    private String video_height;
    private String video_bitrate;
    private String audio_bitrate;
    private String duration;
    private String original_filesize;
    private String transcoded_filesize;
    private String status;

    public String getTftpProgress() {
        return tftp_progress;
    }

    public String getVideoWidth() {
        return video_width;
    }

    public String getVideoHeight() {
        return video_height;
    }

    public String getVideoBitrate() {
        return video_bitrate;
    }

    public String getAudioBitrate() {
        return audio_bitrate;
    }

    public String getDuration() {
        return duration;
    }

    public String getOriginalFilesize() {
        return original_filesize;
    }

    public String getTranscodedFilesize() {
        return transcoded_filesize;
    }

    public String getStatus() {
        return status;
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
