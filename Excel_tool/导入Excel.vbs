'��ʼ
Option Explicit

Dim mdl ' the current model
Set mdl = ActiveModel
If (mdl Is Nothing) Then
   MsgBox "There is no Active Model"
End If

Dim HaveExcel
Dim RQ
RQ = vbYes 'MsgBox("Is Excel Installed on your machine ?", vbYesNo + vbInformation, "Confirmation")
If RQ = vbYes Then
   HaveExcel = True
   ' Open & Create Excel Document
   Dim x1  '
   Set x1 = CreateObject("Excel.Application")
   x1.Workbooks.Open "E:\Excel_tool\table.xls"   'ָ��excel�ĵ�·��
   x1.Workbooks(1).Worksheets("Sheet1").Activate   'ָ��Ҫ�򿪵�sheet����
Else
   HaveExcel = False
End If

a x1, mdl

sub a(x1, mdl)
dim rwIndex   
dim tableName
dim colname
dim table
dim col
dim count

on error Resume Next

set table = mdl.Tables.CreateNew '����һ����ʵ��
table.Name = "��Ƭ��Ϣ��"    'ָ�������������Excel�ĵ����У�Ҳ���� .Cells(rwIndex, 3).Value ����ָ��
table.Code = "AM_CARDINFO"  'ָ������
count = count + 1

For rwIndex = 2 To 1000   'ָ��Ҫ������Excel�б�  ���ڵ�1���Ǳ�ͷ���ӵ�2�п�ʼ
        With x1.Workbooks(1).Worksheets("Sheet1")
            If .Cells(rwIndex, 1).Value = "" Then
               Exit For
            End If

               set col = table.Columns.CreateNew   '����һ��/�ֶ�
               'MsgBox .Cells(rwIndex, 1).Value, vbOK + vbInformation, "��"
               If .Cells(rwIndex, 3).Value = "" Then
                  col.Name = .Cells(rwIndex, 1).Value   'ָ������
               Else 
                  col.Name = .Cells(rwIndex, 3).Value
               End If
               'MsgBox col.Name, vbOK + vbInformation, "��"
               col.Code = .Cells(rwIndex, 1).Value   'ָ������
               
               col.DataType = .Cells(rwIndex, 2).Value   'ָ������������
               
               col.Comment = .Cells(rwIndex, 5).Value  'ָ����˵��
               
               If .Cells(rwIndex, 4).Value = "��" Then
                   col.Mandatory = true        'ָ�����Ƿ�ɿ�  true  Ϊ���ɿ�                  
               End If
               
               If rwIndex = 2 Then
                   col.Primary = true    'ָ������
               End If
        End With
Next
MsgBox "�������ݱ�ṹ���� " + CStr(count), vbOK + vbInformation, "��"

Exit Sub
End sub
