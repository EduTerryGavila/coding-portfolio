###################
# Datos
	.data

$str1:
	.asciiz "Hola mundo"
###################
# Seccion de codigo
	.text
	.globl main
main:
	li $v0, 4
	la $a0, $str1
	syscall
# Exit
	li $v0, 10
	syscall
