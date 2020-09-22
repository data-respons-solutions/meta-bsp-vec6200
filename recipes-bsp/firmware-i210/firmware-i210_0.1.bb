DESCRIPTION = "i210 firmware"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI += " \
	file://i210-vec6200-invm.hex \
"

RRECOMMENDS_${PN} += "flash-i210"

do_install () {
    install -d ${D}${nonarch_base_libdir}/firmware/intel
    install -m 0644 ${WORKDIR}/i210-vec6200-invm.hex ${D}${nonarch_base_libdir}/firmware/intel/
}

FILES_${PN} += "${nonarch_base_libdir}/firmware/intel/*"

COMPATIBLE_MACHINE = "vec6200"
