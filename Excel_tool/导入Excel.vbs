'开始
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
   x1.Workbooks.Open "E:\Excel_tool\table.xls"   '指定excel文档路径
   x1.Workbooks(1).Worksheets("Sheet1").Activate   '指定要打开的sheet名称
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

set table = mdl.Tables.CreateNew '创建一个表实体
table.Name = "卡片信息表"    '指定表名，如果在Excel文档里有，也可以 .Cells(rwIndex, 3).Value 这样指定
table.Code = "AM_CARDINFO"  '指定表名
count = count + 1

For rwIndex = 2 To 1000   '指定要遍历的Excel行标  由于第1行是表头，从第2行开始
        With x1.Workbooks(1).Worksheets("Sheet1")
            If .Cells(rwIndex, 1).Value = "" Then
               Exit For
            End If

               set col = table.Columns.CreateNew   '创建一列/字段
               'MsgBox .Cells(rwIndex, 1).Value, vbOK + vbInformation, "列"
               If .Cells(rwIndex, 3).Value = "" Then
                  col.Name = .Cells(rwIndex, 1).Value   '指定列名
               Else 
                  col.Name = .Cells(rwIndex, 3).Value
               End If
               'MsgBox col.Name, vbOK + vbInformation, "列"
               col.Code = .Cells(rwIndex, 1).Value   '指定列名
               
               col.DataType = .Cells(rwIndex, 2).Value   '指定列数据类型
               
               col.Comment = .Cells(rwIndex, 5).Value  '指定列说明
               
               If .Cells(rwIndex, 4).Value = "否" Then
                   col.Mandatory = true        '指定列是否可空  true  为不可空                  
               End If
               
               If rwIndex = 2 Then
                   col.Primary = true    '指定主键
               End If
        End With
Next
MsgBox "生成数据表结构共计 " + CStr(count), vbOK + vbInformation, "表"

Exit Sub
End sub
