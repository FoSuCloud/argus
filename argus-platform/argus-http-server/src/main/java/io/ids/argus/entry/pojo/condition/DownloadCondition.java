package io.ids.argus.entry.pojo.condition;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class DownloadCondition {
    @NotBlank
    private String fileId;
}
