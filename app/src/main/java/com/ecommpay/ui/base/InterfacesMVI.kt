package com.ecommpay.ui.base

//Намерения пользователя/view что-то сделать
//(например: обновить список, открыть экран и т.д.).
//Намерения пораждаются пользователем в процессе взаимодействия с ui (например, нажатием на кнопку) ИЛИ другим view
//(например, когда нужно изменить состояние другого экрна или запустить действие на нем, в таком случае
// необходимо иметь доступ к ViewModel этого экрана).
//Все intents обрабатываются в ViewModel,
//ViewModel в свою очередь меняет viewState или запускает Action
//View(ViewModel(Intent))
//ViewModel(Intent) = ViewAction/ViewState
//View(ViewAction/ViewState) - отрисовка
interface ViewIntents

//Данные для отображения
//Данные для отображения могут быть 3 видов:
//1. String - данные для текстовых полей
//2. @Res Int - данные всех ресурсов(изьятием данных по id занимается сама вью)
//3. ViewIntents
//Если для формирования intent требуются специфические данные,
// не относящиеся к отображению, то следует передавать
// уже сформированный intent с нужными данными в самой ViewData
interface ViewData

//Состояния экрана
//(например: загрузка, отображение данных, пустой список и т.д.)
abstract class ViewStates<VD : ViewData>(open val viewData: VD)

//Действия, которые не влияют на общее состояние экрана
//(например: Toast, переход в другой экран, показ диалога, открытие другого приложения и т.д. )
abstract class ViewActions : Action()
