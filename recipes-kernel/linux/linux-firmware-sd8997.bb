DESCRIPTION = "Marwell SD8997 WIFI firmware"
LICENSE = "Firmware-Marvell"
LIC_FILES_CHKSUM = "file://LICENCE.Marvell;md5=28b6ed8bd04ba105af6e4dcd6e997772"
NO_GENERIC_LICENSE[Firmware-Marvell] = "LICENCE.Marvell"

SRC_URI = "file://sdsd8997_combo_v4.bin file://LICENCE.Marvell"

S = "${WORKDIR}"

do_install () {
	install -d ${D}/${nonarch_base_libdir}/firmware/mrvl
	install -m 0644 ${S}/sdsd8997_combo_v4.bin ${D}/${nonarch_base_libdir}/firmware/mrvl/sdsd8997_combo_v4.bin
}

FILES_${PN} += "${nonarch_base_libdir}/firmware/mrvl/*"
