package com.solproe.business.repository;

public sealed interface ReportState permits ReportState.Initial, ReportState.Loading, ReportState.Success, ReportState.Error {

    record Initial() implements ReportState{}

    record Loading() implements ReportState{}

    record Success(String message) implements ReportState{}

    record Error(String errorMessage) implements ReportState{}
}
