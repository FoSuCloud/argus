package io.ids.argus.extension.modelchecking.job;

import io.ids.argus.core.base.json.Transformer;
import io.ids.argus.core.conf.log.ArgusLogger;
import io.ids.argus.job.client.process.ProcessJob;
import io.ids.argus.module.conf.ModuleProperties;

import java.io.File;
import java.nio.file.Paths;
import java.util.UUID;

public class ModelCheckingJob extends ProcessJob<ModelCheckingJobParams, ModelCheckingJobResult> {
    private final ArgusLogger log = new ArgusLogger(ModelCheckingJob.class);

    public ModelCheckingJob(String seq, String params) {
        super(seq, params);
        moduleName  = "extensions";
        extensionName = ModuleProperties.get().getName();
        fileName = this.getParams().getPath();
    }

    @Override
    public ModelCheckingJobParams transform(String params) {
        return Transformer.fromJson(params, ModelCheckingJobParams.class);
    }

    @Override
    public ModelCheckingJobResult onRun() {
        String result = null;
        if (running()) {
            result = this.scan();
        }
        return new ModelCheckingJobResult(result);
    }

    private String scan() {
        try {
            pb.command("node", this.getScanScriptCommand(), this.getModelFilePath());
            String res = runProcess();
            uploadFile(fileName, res.getBytes(), "result");
            return null;
        } catch (Exception e) {
            log.error("scan error:{}", e.getMessage());
        }
        return null;
    }

    private void insertFile(){
        var fileId = UUID.randomUUID().toString();
    }

    private String getScanScriptCommand() {
        return Paths.get("argus-extension/model-checking/modelchecking-server/dist").toAbsolutePath() + File.separator + "serve.js";
    }
    private String getModelFilePath() {
        return Paths.get("storage/extensions/modelchecking/model").toAbsolutePath() + File.separator + fileName;
    }

    @Override
    public void onComplete() {
        super.onComplete();
        log.debug("scan task complete");
    }
}
