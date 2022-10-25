/*
@file:Suppress("JAVA_MODULE_DOES_NOT_EXPORT_PACKAGE")
package com.example.countriesdocker.mock

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fif.payments.credibanco.adapter.controller.model.CardRegistrationModelRequest
import com.fif.payments.credibanco.adapter.controller.model.FPayMerchant
import com.fif.payments.credibanco.adapter.controller.model.MetadataRequest
import com.fif.payments.credibanco.adapter.controller.model.RefundTransactionModelRequest
import com.fif.payments.credibanco.adapter.controller.model.RefundTransactionModelRequest.Amount
import com.fif.payments.credibanco.adapter.controller.model.RefundTransactionModelRequest.MetadataRefundRest
import com.fif.payments.credibanco.adapter.controller.model.RefundTransactionModelRequest.OriginalTransactionRefundRequest
import com.fif.payments.credibanco.adapter.controller.model.RefundTransactionModelRequest.OriginalTransactionRefundRest
import com.fif.payments.credibanco.adapter.controller.model.RefundTransactionModelRequest.PayerMetadata
import com.fif.payments.credibanco.adapter.controller.model.RefundTransactionModelRequest.RefundTransactionRest
import com.fif.payments.credibanco.adapter.controller.model.ReverseTransactionModelRequest
import com.fif.payments.credibanco.adapter.controller.model.ReverseTransactionModelRequest.OriginalTransactionRequest
import com.fif.payments.credibanco.adapter.controller.model.ReverseTransactionModelRequest.OriginalTransactionRest
import com.fif.payments.credibanco.adapter.controller.model.ReverseTransactionModelRequest.TransactionRestModel
import com.fif.payments.credibanco.adapter.controller.model.TechnicalSaleReverseModelRequest
import com.fif.payments.credibanco.adapter.controller.model.TransactionControllerModelRequest
import com.fif.payments.credibanco.adapter.controller.model.TransactionMetadataRequest
import com.fif.payments.credibanco.adapter.controller.model.TransactionRequest
import com.fif.payments.credibanco.adapter.rest.model.DetokenizedCardVO
import com.fif.payments.credibanco.adapter.rest.model.ResponseCode
import com.fif.payments.credibanco.config.SPError
import com.fif.payments.credibanco.config.exception.CredibancoBusinessErrorException
import com.fif.payments.credibanco.config.exception.CredibancoTechnicalErrorException
import com.fif.payments.credibanco.config.exception.GenericException
import com.fif.payments.credibanco.domain.Card
import com.fif.payments.credibanco.domain.CardType
import com.fif.payments.credibanco.domain.CardVerificationTransaction
import com.fif.payments.credibanco.domain.CountryConfiguration
import com.fif.payments.credibanco.domain.CredibancoRefundTransaction
import com.fif.payments.credibanco.domain.CredibancoReverseTransaction
import com.fif.payments.credibanco.domain.CredibancoTransaction
import com.fif.payments.credibanco.domain.Detail
import com.fif.payments.credibanco.domain.DetokenizedCard
import com.fif.payments.credibanco.domain.MerchantMetadata
import com.fif.payments.credibanco.domain.PaymentType
import com.fif.payments.credibanco.domain.ReferencesTransactionCommitted
import com.fif.payments.credibanco.domain.RefundTransaction
import com.fif.payments.credibanco.domain.ResponseType
import com.fif.payments.credibanco.domain.TokenizationMethod.CENTER
import com.fif.payments.credibanco.domain.TokenizedCard
import com.fif.payments.credibanco.domain.Transaction
import com.fif.payments.credibanco.domain.TransactionCommitted
import com.fif.payments.credibanco.domain.TransactionResult
import org.springframework.http.HttpStatus
import java.math.BigDecimal
import java.time.LocalDateTime

object MockFactory {

    private const val AUTHORIZATION_CODE_MOCK = "123456789"
    private const val ACQUIRER_UNIQUE_ID = "2855"
    private const val IP_ADDRESS_MOCK = "192.168.0.1"
    private const val CARD_TYPE_MOCK = "CREDIT"
    private const val CARD_TOKEN_MOCK = "4051881132496623"
    private const val CARD_TOKEN_VERSION_MOCK =
        "eyJrZXkiOiAiZWQzNzM4YWFiYWQxNGE0Y2FjNDlhNTMzMjExYzI4MTUiLCAic2FsdCI6ICI3MDA4MWIwZTRmMTc0NDk2ODE2OGJjYzAxMmJhZWQ1MyJ9"
    private const val DETOKENIZED_CARD_NUMBER_MOCK = "5303710409428783"
    const val DETOKENIZED_CARD_NUMBER_OBFUSCATE_MOCK = "XXXX-XXXX-XXXX-8783"
    private const val CARD_EXPIRATION_DATE_MOCK = "0621"
    private const val CARD_CVV_NUMBER = "3555"
    private const val CARD_TOKEN_MESSAGE = "TOKENIZED"
    private const val RECONCILIATION_ID_MOCK = "12345"
    private const val PAYMENT_METHOD_MOCK = "CREDIT"
    private const val MASTERCARD_BRAND_MOCKED = "MASTERCARD"
    private const val AMEX_BRAND_MOCKED = "AMERICAN EXPRESS"
    const val SUBMERCHANT_ID_MOCK = "5678"
    const val TERMINAL_ID_MOCK = "00004451"
    val TRANSACTION_DATE_TIME_MOCK = LocalDateTime.now().withNano(0)
    val UNIQUE_ID_MOCK = "1234"
    const val TOTAL_AMOUNT_MOCK = 100
    const val ID_TRX_MOCK = "2855"

    // adapter mock
    fun getTransactionControllerModelRequest(
        brand: String = MASTERCARD_BRAND_MOCKED,
        tokenizationMethod: String? = null
    ) = TransactionControllerModelRequest(
        transactionType = "SALE",
        paymentMethodDetails = listOf(
            TransactionControllerModelRequest.PaymentMethodDetail(
                card = TransactionControllerModelRequest.Card(
                    number = CARD_TOKEN_MOCK,
                    expirationDate = CARD_EXPIRATION_DATE_MOCK,
                    metadata = TransactionControllerModelRequest.MetadataCard(
                        tokenVersion = CARD_TOKEN_VERSION_MOCK,
                        brand = brand,
                        tokenizationMethod = tokenizationMethod
                    )
                )
            )
        ),
        transaction = TransactionControllerModelRequest.TransactionRest(
            installments = TransactionControllerModelRequest.Installments(
                number = 1
            ),
            uniqueId = UNIQUE_ID_MOCK,
            datetime = TRANSACTION_DATE_TIME_MOCK
        ),
        metadata = getMetadataTransaction()
    )

    fun getMetadataTransaction() = TransactionControllerModelRequest.Metadata(
        paymentMethod = PAYMENT_METHOD_MOCK,
        fpayMerchant = getFPayMerchant(),
        transaction = getTransactionMetadata()
    )

    fun getTransactionMetadata() = TransactionControllerModelRequest.TransactionMetadata(
        reconciliationId = RECONCILIATION_ID_MOCK,
        ipAddress = IP_ADDRESS_MOCK,
        amount = TransactionControllerModelRequest.AmountMetadata(
            details = TransactionControllerModelRequest.DetailAmount(
                tax = BigDecimal.ONE,
                subtotal = BigDecimal(1234)
            )
        )
    )

    fun getAmountMetadataInvalid() = TransactionControllerModelRequest.AmountMetadata(
        details = TransactionControllerModelRequest.DetailAmount(
            tax = BigDecimal(12345),
            subtotal = BigDecimal(1234)
        )
    )

    fun getCardRegistrationModelRequest(
        buyOrder: String
    ) =
        CardRegistrationModelRequest(
            cardType = "CREDIT",
            buyOrder = buyOrder,
            brandName = "AMERICAN EXPRESS",
            number = CARD_TOKEN_MOCK,
            expirationDate = CARD_EXPIRATION_DATE_MOCK,
            cvv = CARD_CVV_NUMBER,
            merchantId = ACQUIRER_UNIQUE_ID,
            terminalId = TERMINAL_ID_MOCK
        )

    fun getPaymentMethodDetailTest() = PaymentMethodDetailTest(
        card = CardTest(
            number = CARD_TOKEN_MOCK,
            expirationDate = CARD_EXPIRATION_DATE_MOCK,
            metadata = MetadataCardTest(
                tokenVersion = CARD_TOKEN_VERSION_MOCK,
                brand = "MASTERCARD",
                cardType = "CREDIT"
            )
        )
    )

    fun getValidCardRegistrationModelRequestTest() = CardRegistrationModelRequestTest(
        cardType = CARD_TYPE_MOCK,
        buyOrder = "123456",
        brandName = "MASTERCARD",
        number = CARD_TOKEN_MOCK,
        expirationDate = CARD_EXPIRATION_DATE_MOCK,
        cvv = CARD_CVV_NUMBER,
        terminalId = TERMINAL_ID_MOCK,
        merchantId = ACQUIRER_UNIQUE_ID
    )

    fun getValidTransactionControllerModelRequestTest() = TransactionControllerModelRequestTest(
        paymentMethodDetails = listOf(
            PaymentMethodDetailTest(
                card = CardTest(
                    number = CARD_TOKEN_MOCK,
                    expirationDate = CARD_EXPIRATION_DATE_MOCK,
                    metadata = MetadataCardTest(
                        tokenVersion = CARD_TOKEN_VERSION_MOCK,
                        brand = "MASTERCARD",
                        cardType = "CREDIT"
                    )
                )
            )
        ),
        transaction = TransactionTest(
            installments = InstallmentsTest(
                number = 1
            ),
            uniqueId = "12345",
            datetime = TRANSACTION_DATE_TIME_MOCK
        ),
        metadata = MetadataTest(
            paymentMethod = PAYMENT_METHOD_MOCK,
            fPayMerchant = FPayMerchantTest(
                name = "name",
                merchantId = "1234",
                submerchantId = "5678",
                terminalId = TERMINAL_ID_MOCK
            ),
            transaction = TransactionMetadataTest(
                reconciliationId = RECONCILIATION_ID_MOCK,
                ipAddress = IP_ADDRESS_MOCK,

                amount = AmountMetadaTest(
                    details = DetailAmountTest(
                        tax = BigDecimal.ONE,
                        subtotal = BigDecimal(1234)
                    )
                )
            ),
        )
    )
    fun getResponseType(
        authorizationCode: String = AUTHORIZATION_CODE_MOCK,
        responseCode: String = ResponseCode.APPROVED.code,
        transactionDateTime: LocalDateTime? = null,
        uniqueId: String? = null,
    ) = ResponseType(
        responseCode = responseCode,
        responseDescription = ResponseCode.APPROVED.message,
        authorizationCode = authorizationCode,
        acquirerUniqueId = ACQUIRER_UNIQUE_ID,
        uniqueId = uniqueId,
        transactionDatetime = transactionDateTime
    )

    fun getResponseTypeRefund() = ResponseType(
        responseCode = ResponseCode.APPROVED_REFUND.code,
        responseDescription = ResponseCode.APPROVED_REFUND.message,
        authorizationCode = "",
        acquirerUniqueId = ID_TRX_MOCK,
        uniqueId = null,
        transactionDatetime = null
    )

    fun getResponseTypeCardRegistration(
        authorizationCode: String = AUTHORIZATION_CODE_MOCK,
        responseCode: String = ResponseCode.APPROVED.code,
        transactionDateTime: LocalDateTime? = TRANSACTION_DATE_TIME_MOCK,
        uniqueId: String = UNIQUE_ID_MOCK
    ) =
        getResponseType(authorizationCode, responseCode, transactionDateTime, uniqueId)

    fun getParametrizedRequest(transactionControllerModelRequestTest: TransactionControllerModelRequestTest): String {
        val objectMapper = ObjectMapper()
        objectMapper.propertyNamingStrategy = PropertyNamingStrategies.SNAKE_CASE
        objectMapper.findAndRegisterModules()
        return objectMapper
            .writeValueAsString(transactionControllerModelRequestTest)
    }

    fun getParametrizedRequestCard(request: CardRegistrationModelRequestTest): String {
        val objectMapper = ObjectMapper()
        objectMapper.propertyNamingStrategy = PropertyNamingStrategies.SNAKE_CASE
        return objectMapper
            .writeValueAsString(request)
    }

    fun getTransaction(
        transactionDate: LocalDateTime? = TRANSACTION_DATE_TIME_MOCK
    ) =
        Transaction(
            paymentMethod = PaymentType.CREDIT,
            reconciliationId = "buyOrdertest-1",
            uniqueId = "12345",
            tokenizedCard = getTokenizedCard(),
            cardExpirationDate = CARD_EXPIRATION_DATE_MOCK,
            detail = Detail(
                amount = BigDecimal.ZERO,
                buyOrder = "buyOrderTest-2",
                installmentsNumber = 1,
                tax = BigDecimal.ZERO
            ),
            ipAddress = IP_ADDRESS_MOCK,
            brand = MASTERCARD_BRAND_MOCKED,
            submerchantId = SUBMERCHANT_ID_MOCK,
            terminalId = TERMINAL_ID_MOCK,
            dateTime = transactionDate!!
        )

    fun getCardVerificationTransaction() = CardVerificationTransaction(
        traceId = UNIQUE_ID_MOCK,
        card = getCard(),
        merchantMetadata = getMerchantMetadata()

    )

    private fun getMerchantMetadata() =
        MerchantMetadata(
            merchantId = ACQUIRER_UNIQUE_ID,
            terminalId = TERMINAL_ID_MOCK
        )

    fun getTokenizedCard() =
        TokenizedCard(
            token = CARD_TOKEN_MOCK,
            tokenVersion = CARD_TOKEN_VERSION_MOCK,
            tokenizationMethod = CENTER
        )

    fun getCard() =
        Card(
            cardType = CardType.CREDIT,
            brand = AMEX_BRAND_MOCKED,
            number = CARD_TOKEN_MOCK,
            expirationDate = CARD_EXPIRATION_DATE_MOCK,
            cvv = CARD_CVV_NUMBER
        )

    fun getDetokenizedCard() =
        DetokenizedCard(
            cardNumber = DETOKENIZED_CARD_NUMBER_MOCK
        )

    fun getDetokenizedCardVO() =
        DetokenizedCardVO(
            message = CARD_TOKEN_MESSAGE,
            pan = DETOKENIZED_CARD_NUMBER_MOCK
        )

    fun getTransactionResultWithTechnicalError() = TransactionResult
        .TransactionError(CredibancoTechnicalErrorException(SPError.CREDIBANCO_TECHNICAL_ERROR))

    fun getTransactionResultWithBusinessError() = TransactionResult
        .TransactionError(CredibancoBusinessErrorException(SPError.CREDIBANCO_BUSINESS_ERROR))

    fun getReverseTransactionControllerModelRequest() = ReverseTransactionModelRequest(
        transaction = TransactionRestModel(
            originalTransaction = OriginalTransactionRequest(
                transaction = OriginalTransactionRest(
                    acquirerUniqueId = ACQUIRER_UNIQUE_ID
                )
            ),
            uniqueId = "1234",
            datetime = TRANSACTION_DATE_TIME_MOCK
        ),
        metadata = ReverseTransactionModelRequest.ReverseTransactionMetadata(
            fpayMerchant = getFPayMerchant()
        )
    )

    fun getRefundTransactionControllerModelRequest() = RefundTransactionModelRequest(
        transaction = RefundTransactionRest(
            amount = Amount(
                total = BigDecimal(TOTAL_AMOUNT_MOCK)
            ),
            originalTransaction = OriginalTransactionRefundRequest(
                transaction = OriginalTransactionRefundRest(
                    acquirerUniqueId = ACQUIRER_UNIQUE_ID,
                    authorizationCode = AUTHORIZATION_CODE_MOCK
                )
            ),
            uniqueId = UNIQUE_ID_MOCK,
            datetime = TRANSACTION_DATE_TIME_MOCK
        ),
        metadata = getMetadataRefundRest(getPayer()),
        paymentMethodDetails = listOf(
            RefundTransactionModelRequest.PaymentMethodDetail(
                card = RefundTransactionModelRequest.Card(
                    number = CARD_TOKEN_MOCK,
                    metadata = RefundTransactionModelRequest.MetadataCard(
                        tokenVersion = CARD_TOKEN_VERSION_MOCK
                    )
                )
            )
        )
    )

    fun getMetadataRefundRest(
        payerMetadata: PayerMetadata? = null
    ) = MetadataRefundRest(
        fpayMerchant = getFPayMerchant(),
        payer = payerMetadata
    )

    fun getCredibancoReverseTransaction(transactionDateTime: LocalDateTime = TRANSACTION_DATE_TIME_MOCK) = CredibancoReverseTransaction(
        transactionId = ACQUIRER_UNIQUE_ID.toLong(),
        submerchantId = SUBMERCHANT_ID_MOCK,
        uniqueId = UNIQUE_ID_MOCK,
        dateTime = transactionDateTime,
        terminalId = TERMINAL_ID_MOCK
    )

    fun getCredibancoRefundTransaction(transactionDateTime: LocalDateTime = TRANSACTION_DATE_TIME_MOCK) = RefundTransaction(
        authorizationCode = AUTHORIZATION_CODE_MOCK,
        acquirerUniqueId = ACQUIRER_UNIQUE_ID,
        submerchantId = SUBMERCHANT_ID_MOCK,
        transactionTotalAmount = TOTAL_AMOUNT_MOCK.toBigDecimal(),
        uniqueId = UNIQUE_ID_MOCK,
        dateTime = transactionDateTime,
        tokenizedCard = getTokenizedCard(),
        clientName = "DATO FIJO",
        clientEmail = "xxxxx@xxxx.gmail.com",
        clientPhone = "9999999999",
        terminalId = TERMINAL_ID_MOCK
    )

    fun getRefundTransaction(transactionDateTime: LocalDateTime = TRANSACTION_DATE_TIME_MOCK) = CredibancoRefundTransaction(
        refundTransaction = RefundTransaction(
            authorizationCode = AUTHORIZATION_CODE_MOCK,
            acquirerUniqueId = ACQUIRER_UNIQUE_ID,
            submerchantId = SUBMERCHANT_ID_MOCK,
            terminalId = TERMINAL_ID_MOCK,
            transactionTotalAmount = TOTAL_AMOUNT_MOCK.toBigDecimal(),
            uniqueId = UNIQUE_ID_MOCK,
            dateTime = transactionDateTime,
            clientName = "DATO FIJO",
            clientEmail = "xxxxxx@xxxxxx.com",
            clientPhone = "999999999",
            tokenizedCard = getTokenizedCard()
        ),
        detokenizedCard = getDetokenizedCard()
    )

    fun getTechnicalReverseRequest(transactionDateTime: LocalDateTime) = TechnicalSaleReverseModelRequest(
        metadata = MetadataRequest(
            transaction = TransactionMetadataRequest(reconciliationId = "1"),
            fpayMerchant = getFPayMerchant()
        ),
        transaction = TransactionRequest(datetime = transactionDateTime, uniqueId = UNIQUE_ID_MOCK)
    )
    fun getCountryConfiguration() = CountryConfiguration(timeZone = "-05:00")

    fun getTransactionsCommitted() = listOf(
        TransactionCommitted(
            transactionId = 11,
            transactionState = "aprobado",
            stateCode = "00",
            references = listOf(
                ReferencesTransactionCommitted(
                    referenceKey = "referenceKey",
                    referenceDescription = "11"
                )
            )
        ),
        TransactionCommitted(
            transactionId = 22,
            transactionState = "aprobado",
            stateCode = "00",
            references = listOf(
                ReferencesTransactionCommitted(
                    referenceKey = "referenceKey",
                    referenceDescription = "22"
                )
            )
        ),
        TransactionCommitted(
            transactionId = 33,
            transactionState = "aprobado",
            stateCode = "00",
            references = listOf(
                ReferencesTransactionCommitted(
                    referenceKey = "referenceKey",
                    referenceDescription = "33"
                )
            )
        )
    )
    fun getCredibancoTransaction() = CredibancoTransaction(
        getTransaction(),
        getDetokenizedCard()
    )

    fun getListTransactionCommitted() = listOf(
        TransactionCommitted(
            transactionId = 22,
            transactionState = "negado",
            stateCode = "91",
            references = listOf(
                ReferencesTransactionCommitted(
                    referenceKey = "PRUEBAS QA REFERENCIA 1",
                    referenceDescription = "COMPRA TIPO NORMAL JKASDJSDJAKASDJKASKADJ"
                )
            )
        ),
        TransactionCommitted(
            transactionId = 23,
            transactionState = "aprobado",
            stateCode = "00",
            references = listOf(
                ReferencesTransactionCommitted(
                    referenceKey = "PRUEBAS QA REFERENCIA 1",
                    referenceDescription = "COMPRA TIPO NORMAL JKASDJSDJAKASDJKASKADJ"
                )
            )
        )
    )

    fun getFPayMerchant() = FPayMerchant(
        name = "name",
        merchantId = "1234",
        submerchantId = "5678",
        terminalId = TERMINAL_ID_MOCK
    )

    fun getPayer() = PayerMetadata(
        email = "xxxx@xxxx.com",
        firstName = "DATO FIJO",
        phoneNumber = "99999999"
    )

    fun getSearchTransactionsResponse() = "[\n" +
        "    {\n" +
        "        \"transactionId\": 22,\n" +
        "        \"transactionDate\": \"2021-04-15 16:44:12\",\n" +
        "        \"transactionType\": \"Authorization\",\n" +
        "        \"transactionType2\": \"API normal\",\n" +
        "        \"transactionTaxType\": \"Comercio normal\",\n" +
        "        \"pasarela\": \"pasarelas-user-test-2\",\n" +
        "        \"uniqueCode\": 10203040,\n" +
        "        \"terminalId\": \"00004451\",\n" +
        "        \"purchaseAmount\": 16000,\n" +
        "        \"ivaTax\": 4000,\n" +
        "        \"installmentsNumber\": 1,\n" +
        "        \"references\": [\n" +
        "            {\n" +
        "                \"referenceKey\": \"PRUEBAS QA REFERENCIA 1\",\n" +
        "                \"referenceDescription\": \"COMPRA TIPO NORMAL JKASDJSDJAKASDJKASKADJ\"\n" +
        "            }\n" +
        "        ],\n" +
        "        \"currencyCode\": \"170\",\n" +
        "        \"airlinePurchaseAmount\": 100,\n" +
        "        \"agencyPurchaseAmount\": 100,\n" +
        "        \"airporTax\": 100,\n" +
        "        \"airlineCode\": 100,\n" +
        "        \"ivaTaxAirline\": 100,\n" +
        "        \"ivaTaxAdministrativeFee\": 100,\n" +
        "        \"tip\": 100,\n" +
        "        \"iacTax\": 0,\n" +
        "        \"stateCode\": \"91\",\n" +
        "        \"authorizationCode\": \"12\",\n" +
        "        \"mitExpirationDate\": \"2021-12-12 21:00:00\",\n" +
        "        \"transactionState\": \"negado\"\n" +
        "    },\n" +
        "    {\n" +
        "        \"transactionId\": 23,\n" +
        "        \"transactionDate\": \"2021-04-15 16:45:41\",\n" +
        "        \"transactionType\": \"Authorization\",\n" +
        "        \"transactionType2\": \"API normal\",\n" +
        "        \"transactionTaxType\": \"Comercio normal\",\n" +
        "        \"pasarela\": \"pasarelas-user-test-2\",\n" +
        "        \"uniqueCode\": 10203040,\n" +
        "        \"terminalId\": \"00004451\",\n" +
        "        \"purchaseAmount\": 16000,\n" +
        "        \"ivaTax\": 4000,\n" +
        "        \"installmentsNumber\": 1,\n" +
        "        \"references\": [\n" +
        "            {\n" +
        "                \"referenceKey\": \"PRUEBAS QA REFERENCIA 1\",\n" +
        "                \"referenceDescription\": \"COMPRA TIPO NORMAL JKASDJSDJAKASDJKASKADJ\"\n" +
        "            }\n" +
        "        ],\n" +
        "        \"currencyCode\": \"170\",\n" +
        "        \"airlinePurchaseAmount\": 100,\n" +
        "        \"agencyPurchaseAmount\": 100,\n" +
        "        \"airporTax\": 100,\n" +
        "        \"airlineCode\": 100,\n" +
        "        \"ivaTaxAirline\": 100,\n" +
        "        \"ivaTaxAdministrativeFee\": 100,\n" +
        "        \"tip\": 100,\n" +
        "        \"iacTax\": 0,\n" +
        "        \"stateCode\": \"00\",\n" +
        "        \"authorizationCode\": \"123\",\n" +
        "        \"mitExpirationDate\": \"2021-04-15 16:45:41\",\n" +
        "        \"transactionState\": \"aprobado\"\n" +
        "    }\n" +
        "]\n"
}

data class TransactionControllerBadRequestTestParam(
    val request: TransactionControllerModelRequestTest,
    val testName: String
) {
    override fun toString() = testName
}

data class CardRegistrationControllerBadRequestTestParam(
    val request: CardRegistrationModelRequestTest,
    val testName: String
) {
    override fun toString() = testName
}

data class TransactionControllerSuccessParam(
    val request: TransactionControllerModelRequest,
    val testName: String
) {
    override fun toString() = testName
}

data class TransactionControllerModelRequestTest(
    var metadata: MetadataTest? = null,
    var paymentMethodDetails: List<PaymentMethodDetailTest>? = null,
    var transaction: TransactionTest? = null,
    var transactionType: String? = null
)

data class CardRegistrationModelRequestTest(
    var cardType: String? = null,
    var brandName: String? = null,
    var expirationDate: String? = null,
    var cvv: String? = null,
    var number: String? = null,
    var buyOrder: String? = null,
    var terminalId: String? = null,
    var merchantId: String? = null
)

data class MetadataTest(
    var fPayMerchant: FPayMerchantTest? = null,
    var paymentMethod: String? = null,
    var transaction: TransactionMetadataTest? = null
)

data class TransactionMetadataTest(
    var reconciliationId: String? = null,
    var ipAddress: String? = null,
    var amount: AmountMetadaTest? = null
)

data class FPayMerchantTest(
    var name: String? = null,
    var merchantId: String? = null,
    var submerchantId: String? = null,
    var terminalId: String? = null
)

data class TransactionTest(
    var installments: InstallmentsTest? = null,
    var uniqueId: String? = null,
    var datetime: LocalDateTime? = null
)

data class AmountMetadaTest(
    var details: DetailAmountTest? = null
)

data class DetailAmountTest(
    var tax: BigDecimal? = null,
    var subtotal: BigDecimal? = null
)

data class PaymentMethodDetailTest(
    var card: CardTest? = null
)

data class CardTest(
    var expirationDate: String? = null,
    var metadata: MetadataCardTest? = null,
    var number: String? = null
)

data class MetadataCardTest(
    var tokenVersion: String? = null,
    var brand: String? = null,
    var cardType: String? = null,
    var tokenizationMethod: String? = null
)

data class InstallmentsTest(
    var number: Int? = null
)

data class ExceptionHandlerTestParam(
    val exception: GenericException,
    val httpStatus: HttpStatus
)

data class RequestLoggerTestParam(
    val title: String,
    val request: String,
    val expectedMessage: String
) {
    override fun toString() = title
}
*/
