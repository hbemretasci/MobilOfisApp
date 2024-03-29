// Generated by Dagger (https://dagger.dev).
package com.codmine.mukellef.presentation.document_screen;

import com.codmine.mukellef.domain.use_case.document_screen.GetDocuments;
import com.codmine.mukellef.domain.use_case.document_screen.PostDocumentReadingInfo;
import com.codmine.mukellef.domain.use_case.files.ShowDocument;
import com.codmine.mukellef.domain.use_case.splash_screen.GetUserLoginData;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.inject.Provider;

@ScopeMetadata
@QualifierMetadata
@DaggerGenerated
@SuppressWarnings({
    "unchecked",
    "rawtypes"
})
public final class DocumentViewModel_Factory implements Factory<DocumentViewModel> {
  private final Provider<GetDocuments> getDocumentsProvider;

  private final Provider<PostDocumentReadingInfo> postDocumentReadingInfoProvider;

  private final Provider<GetUserLoginData> getUserLoginDataProvider;

  private final Provider<ShowDocument> showDocumentProvider;

  public DocumentViewModel_Factory(Provider<GetDocuments> getDocumentsProvider,
      Provider<PostDocumentReadingInfo> postDocumentReadingInfoProvider,
      Provider<GetUserLoginData> getUserLoginDataProvider,
      Provider<ShowDocument> showDocumentProvider) {
    this.getDocumentsProvider = getDocumentsProvider;
    this.postDocumentReadingInfoProvider = postDocumentReadingInfoProvider;
    this.getUserLoginDataProvider = getUserLoginDataProvider;
    this.showDocumentProvider = showDocumentProvider;
  }

  @Override
  public DocumentViewModel get() {
    return newInstance(getDocumentsProvider.get(), postDocumentReadingInfoProvider.get(), getUserLoginDataProvider.get(), showDocumentProvider.get());
  }

  public static DocumentViewModel_Factory create(Provider<GetDocuments> getDocumentsProvider,
      Provider<PostDocumentReadingInfo> postDocumentReadingInfoProvider,
      Provider<GetUserLoginData> getUserLoginDataProvider,
      Provider<ShowDocument> showDocumentProvider) {
    return new DocumentViewModel_Factory(getDocumentsProvider, postDocumentReadingInfoProvider, getUserLoginDataProvider, showDocumentProvider);
  }

  public static DocumentViewModel newInstance(GetDocuments getDocuments,
      PostDocumentReadingInfo postDocumentReadingInfo, GetUserLoginData getUserLoginData,
      ShowDocument showDocument) {
    return new DocumentViewModel(getDocuments, postDocumentReadingInfo, getUserLoginData, showDocument);
  }
}
