##################
# Seccion de datos
	.data

$str1:
	.asciiz "Hola mundo"
$str2:
	.asciiz " desde la asignatura "
$str3:
	.asciiz "de Compiladores"

###################
# Seccion de codigo
	.text
	.globl main
main:
	li $v0, 4
	la $a0, $str1
	syscall
	li $v0, 4
	la $a0, $str2
	syscall
	li $v0, 4
	la $a0, $str3
	syscall

##################
# Final: exit
	li $v0, 10
	syscall
