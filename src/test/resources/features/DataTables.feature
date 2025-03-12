Feature: DataTables Editor Test

  Scenario: Kullanıcı yeni bir kayıt ekler ve doğrular
    Given Kullanıcı "https://editor.datatables.net/" adresine gider
    When Kullanıcı yeni bir kayıt ekler
    And Kullanıcı eklenen kaydı arar
    Then Kullanıcı aradığı kaydın listede olduğunu doğrular
