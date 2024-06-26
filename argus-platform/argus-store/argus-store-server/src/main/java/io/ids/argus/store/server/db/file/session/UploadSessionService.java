package io.ids.argus.store.server.db.file.session;

import io.grpc.stub.StreamObserver;
import io.ids.argus.store.grpc.file.FileUploadStoreServiceGrpc;
import io.ids.argus.store.grpc.file.UploadRequest;
import io.ids.argus.store.grpc.file.UploadResponse;
import io.ids.argus.store.server.db.file.observer.UploadServerObserver;
import io.ids.argus.store.server.service.IService;

/**
 * Session GRPC service
 */
public class UploadSessionService extends FileUploadStoreServiceGrpc.FileUploadStoreServiceImplBase
        implements IService<FileStoreSession> {

    @Override
    public StreamObserver<UploadRequest> upload(StreamObserver<UploadResponse> responseObserver) {
        return new UploadServerObserver(responseObserver);
    }
}
