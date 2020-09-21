DESCRIPTION = "Utility for flashing i210 iNVM"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI += " \
	file://flash-i210.sh \
	file://i210-vec6200-invm.hex \
"

RDEPENDS_${PN} += "intel-eeprom-access-tool"

do_install () {
    install -d ${D}${sbindir}
    install -m 0755 ${WORKDIR}/flash-i210.sh ${D}${sbindir}/flash-i210
    install -d ${D}${nonarch_base_libdir}/firmware/intel
    install -m 0644 ${WORKDIR}/i210-vec6200-invm.hex ${D}${nonarch_base_libdir}/firmware/intel/
}

FILES_${PN} += "${nonarch_base_libdir}/firmware/intel/*"